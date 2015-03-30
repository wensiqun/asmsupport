# Release Notes

## 0.4

### Feature

1. 增加全新Dummy方式API
2. 采用链式结构作为执行队列
3. 重构程序块实现
4. 采用asm 0.5版本
5. 第三方jar包零依赖
6. 简化优化接口设计
7. 修复部分bug

### 已知Issue

1. 无泛型支持
2. 无法创建内部类
3. 无法创建循环引用
4. 目前只支持生成最高1.6的jdk版本的class。因为目前asmsupport未支持"Stack map table".
