package jw.asmsupport.utils.finder.clazz;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassFetcher {
    
	private static final Log LOG = LogFactory.getLog(ClassFetcher.class);

    protected  Collection<URL> urls;
    
    protected boolean extractBaseInterfaces;
    
    protected ClassLoader classLoader;
    
    protected List<String> classNames;
    
    protected Filter fetchFilter;

    public ClassFetcher(ClassLoader classLoader) throws Exception {
        this(classLoader, new Filter(){
			@Override
			public boolean filter(String name) {
				return false;
			}
        	
        });
    }

    
    public ClassFetcher(ClassLoader classLoader, Filter fetchFilter) throws Exception {
        this(classLoader, true, fetchFilter);
    }

    public ClassFetcher(ClassLoader classLoader, boolean excludeParent, Filter fetchFilter) throws Exception {
        this(classLoader, getUrls(classLoader, excludeParent), fetchFilter);
    }

    /**
     * Creates a ClassFinder that will search the urls in the specified classloader excluding
     * the urls in the 'exclude' ClassLoader.
     *
     * @param classLoader source of classes to scan
     * @param exclude source of classes to exclude from scanning
     * @throws Exception if something goes wrong
     */
    public ClassFetcher(ClassLoader classLoader, ClassLoader exclude, Filter fetchFilter) throws Exception {
        this(classLoader, getUrls(classLoader, exclude), fetchFilter);
    }

    public ClassFetcher(ClassLoader classLoader, URL url, Filter fetchFilter) {
        this(classLoader, Arrays.asList(url), fetchFilter);
    }

    public ClassFetcher(ClassLoader classLoader, Filter fetchFilter, String... dirNames) {
        this(classLoader, getURLs(classLoader, dirNames), fetchFilter);
    }
    
    public ClassFetcher(ClassLoader classLoader, Collection<URL> urls, Filter fetchFilter) {
        this(classLoader, urls, false, fetchFilter);
    }

    public ClassFetcher(ClassLoader classLoader, Collection<URL> urls, boolean extractBaseInterfaces, Filter fetchFilter) {
        this(classLoader, urls, extractBaseInterfaces, new HashSet<String>(){
			private static final long serialVersionUID = -2518600163107096151L;
			{
                add("jar");
            }
        }, fetchFilter);
    }

    public ClassFetcher(ClassLoader classLoader, Collection<URL> urls, boolean extractBaseInterfaces, Set<String> protocols, Filter fetchFilter) {
        this.classLoader = classLoader;
        this.extractBaseInterfaces = extractBaseInterfaces;
        this.urls = urls;
        this.classNames = fetchClassNamesFromUrls(urls, protocols, fetchFilter);
    }

    private static List<URL> getURLs(ClassLoader classLoader, String[] dirNames) {
        List<URL> urls = new ArrayList<URL>();
        for (String dirName : dirNames) {
            try {
                Enumeration<URL> classLoaderURLs = classLoader.getResources(dirName);
                while (classLoaderURLs.hasMoreElements()) {
                    URL url = classLoaderURLs.nextElement();
                    urls.add(url);
                }
            } catch (IOException ioe) {
                if (LOG.isErrorEnabled())
                    LOG.error("Could not read driectory ["+dirName+"]", ioe);
            }
        }

        return urls;
    }
    
    public static List<String> fetchClassNamesFromUrls(Collection<URL> urls, Set<String> protocols, Filter filter){
		ArrayList<String> classNames = new ArrayList<String>();
    	for (URL location : urls) {
            try {
                if (protocols.contains(location.getProtocol())) {
                    classNames.addAll(ClassFetcher.jar(location, filter));
                } else if ("file".equals(location.getProtocol())) {
                    try {
                        // See if it's actually a jar
                        URL jarUrl = new URL("jar", "", location.toExternalForm() + "!/");
                        JarURLConnection juc = (JarURLConnection) jarUrl.openConnection();
                        juc.getJarFile();
                        classNames.addAll(ClassFetcher.jar(jarUrl, filter));
                    } catch (IOException e) {
                        classNames.addAll(ClassFetcher.file(location, filter));
                    }
                }
            } catch (Exception e) {
                if (LOG.isErrorEnabled())
                    LOG.error("Unable to read URL ["+location.toExternalForm()+"]", e);
            }
        }
    	return classNames;
    }
    
    public static Collection<URL> getUrls(ClassLoader classLoader, boolean excludeParent) throws IOException {
        return getUrls(classLoader, excludeParent? classLoader.getParent() : null);
    }

    private static Collection<URL> getUrls(ClassLoader classLoader, ClassLoader excludeParent) throws IOException {
        UrlSet urlSet = new UrlSet(classLoader);
        if (excludeParent != null){
            urlSet = urlSet.exclude(excludeParent);
        }
        return urlSet.getUrls();
    }

    public static List<String> file(URL location, Filter fetchFilter) {
        List<String> classNames = new ArrayList<String>();
        @SuppressWarnings("deprecation")
		File dir = new File(URLDecoder.decode(location.getPath()));
        if ("META-INF".equals(dir.getName())) {
            dir = dir.getParentFile(); // Scrape "META-INF" off
        }
        if (dir.isDirectory()) {
            scanDir(dir, classNames, "", fetchFilter);
        }
        return classNames;
    }

    private static void scanDir(File dir, List<String> classNames, String packageName, Filter fetchFilter) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                scanDir(file, classNames, packageName + file.getName() + ".", fetchFilter);
            } else if (file.getName().endsWith(".class")) {
                String name = file.getName();
                name = name.replaceFirst(".class$", "");
                // Classes packaged in an exploded .war (e.g. in a VFS file system) should not
                // have WEB-INF.classes in their package name.
                name = StringUtils.removeStart(packageName, "WEB-INF.classes.") + name;
                if(!fetchFilter.filter(name)){
                    classNames.add(name);
                }
            }
        }
    }

    public static List<String> jar(URL location, Filter filter) throws IOException {
        URL url = URLUtil.normalizeToFileProtocol(location);
        if (url != null) {
            InputStream in = url.openStream();
            try {
                JarInputStream jarStream = new JarInputStream(in);
                return jar(jarStream, filter);
            } finally {
                in.close();
            }
        } else if (LOG.isDebugEnabled())
            LOG.debug("Unable to read ["+location.toExternalForm()+"]");
        
        return Collections.emptyList();
    }

    private static List<String> jar(JarInputStream jarStream, Filter filter) throws IOException {
        List<String> classNames = new ArrayList<String>();

        JarEntry entry;
        while ((entry = jarStream.getNextJarEntry()) != null) {
            if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                continue;
            }
            String className = entry.getName();
            className = className.replaceFirst(".class$", "");

            //war files are treated as .jar files, so takeout WEB-INF/classes
            className = StringUtils.removeStart(className, "WEB-INF/classes/"); 

            className = className.replace('/', '.');
            if(!filter.filter(className)){
                classNames.add(className);
            }
        }

        return classNames;
    }
    
    protected String javaName(String name) {
        return (name == null)? null:name.replace('/', '.');
    }
    
}


