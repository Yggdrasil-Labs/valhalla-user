# Changelog

## [1.1.1](https://github.com/Yggdrasil-Labs/valhalla-user/compare/v1.1.0...v1.1.1) (2026-02-01)


### ♻️ Code Refactoring

* 子模块目录已重命名为 valhalla-user-* ([210ed7f](https://github.com/Yggdrasil-Labs/valhalla-user/commit/210ed7f8b04c0641979ecd2ee10b4b59085cc88f))
* 适配层重构，依赖关系调整为直接依赖应用层，更新相关DTO和命令类以支持新的架构 ([99748d1](https://github.com/Yggdrasil-Labs/valhalla-user/commit/99748d16bdce62e0e2f966c83d79c4e593d4a0de))


### 👷 Continuous Integration

* **deps:** bump actions/checkout from 6.0.1 to 6.0.2 ([a74755c](https://github.com/Yggdrasil-Labs/valhalla-user/commit/a74755c12d1c84057ac3ee12b5ec133dd67d44a9))


### 🔧 Miscellaneous Chores

* bump version to 1.1.1-SNAPSHOT for next development cycle ([15ec2c0](https://github.com/Yggdrasil-Labs/valhalla-user/commit/15ec2c0acfb2298940b030099e18f421ec5d75fe))
* update spotless-maven-plugin version to 3.2.1 ([11a6227](https://github.com/Yggdrasil-Labs/valhalla-user/commit/11a622741841d1eb93f9feef987f4d9961bed83b))
* 将 POM 文件和脚本中的 groupId 从 com.yggdrasil.labs 更新为 io.github.yggdrasil-labs，并更改构建脚本的文件权限 ([4a3fc15](https://github.com/Yggdrasil-Labs/valhalla-user/commit/4a3fc15004c18d038cff5d71a98aea11d081d33f))

## [1.1.0](https://github.com/Yggdrasil-Labs/valhalla-user/compare/v1.0.0...v1.1.0) (2025-12-28)


### ✨ Features

* infra层新增DO实体 ([59aa563](https://github.com/Yggdrasil-Labs/valhalla-user/commit/59aa5637bc2c7a916cdcec9fa56f3e74bbeb4302))
* 修改逻辑删除字段的类型与用法 ([b3f9f76](https://github.com/Yggdrasil-Labs/valhalla-user/commit/b3f9f76133fa7c3028cd5a0497486e115d533a98))
* 初始化API、权限和角色关联数据，插入相关API和权限信息，并建立角色与权限的关联关系 ([110c4ae](https://github.com/Yggdrasil-Labs/valhalla-user/commit/110c4ae2777509959dc3d0bd0d360331260fc1c1))
* 增加API删除逻辑，确保只有禁用状态的API可以被删除 ([b0cb741](https://github.com/Yggdrasil-Labs/valhalla-user/commit/b0cb7419364b4bdcbab3869a28f2c3f9f2020ef7))
* 增加API表的版本和状态字段，调整索引约束以支持版本管理 ([cb3c44b](https://github.com/Yggdrasil-Labs/valhalla-user/commit/cb3c44b5303ff8243051afc70c538a4b55a3ddea))
* 完成服务主体功能 ([004235f](https://github.com/Yggdrasil-Labs/valhalla-user/commit/004235fccd50b58d7f578279ea06a0dd3e2e8f5c))
* 数据库初始化脚本 ([20283db](https://github.com/Yggdrasil-Labs/valhalla-user/commit/20283dbd9a77f6bf845daf3e518ecf6a5d793fa4))
* 更新API相关请求和模型，增加版本和状态字段以支持更灵活的API管理 ([8347a2b](https://github.com/Yggdrasil-Labs/valhalla-user/commit/8347a2b8217513032c152f3aad400d952dab0346))
* 用户信息增加注册来源、注册类型 ([433d5a9](https://github.com/Yggdrasil-Labs/valhalla-user/commit/433d5a9e66e653f5741dc2f7c8542dc087727456))


### 🐛 Bug Fixes

* 关联表增加自增主键 ([f0c6599](https://github.com/Yggdrasil-Labs/valhalla-user/commit/f0c6599b5c09c61b504e63eee2d76649ad029eb3))
* 去掉冗余的id参数 ([9c7d356](https://github.com/Yggdrasil-Labs/valhalla-user/commit/9c7d35600b0cf49df099eb686e1d5a0a86073119))
* 统一使用雪花id，使用String类型返回给外部 ([9d60dc6](https://github.com/Yggdrasil-Labs/valhalla-user/commit/9d60dc6a722966cdcf3deb4bdedd0433fa447e75))


### 📝 Documentation

* 优化数据库设计文档 ([f89ab13](https://github.com/Yggdrasil-Labs/valhalla-user/commit/f89ab1369ca9b0aba57bd536a045ff19d0ffcc25))
* 完善README ([74294d7](https://github.com/Yggdrasil-Labs/valhalla-user/commit/74294d773ded4ab1a0b288a972c18ecd89a0555f))
* 新增数据库设计文档 ([e2f32ad](https://github.com/Yggdrasil-Labs/valhalla-user/commit/e2f32ad460a32d9ad8d6853292997dd6e7c7b45d))


### ♻️ Code Refactoring

* 分页接口使用PageResponse对象 ([b9e9ee1](https://github.com/Yggdrasil-Labs/valhalla-user/commit/b9e9ee1539a0aba632d6b79ecec0882320f5d88c))
* 清理不需要的代码 ([33a7046](https://github.com/Yggdrasil-Labs/valhalla-user/commit/33a70463c7c5c78edb2c3c861f5f55bff3a3c280))
* 相关文档重命名valhalla-user ([6a2f099](https://github.com/Yggdrasil-Labs/valhalla-user/commit/6a2f09944139db42632490511ed5817bd26b7156))


### 👷 Continuous Integration

* **deps:** bump actions-ecosystem/action-add-labels from 1.1.0 to 1.1.3 ([6928f59](https://github.com/Yggdrasil-Labs/valhalla-user/commit/6928f59501131030bab52851d2302d3879c94425))
* **deps:** bump actions/github-script from 7 to 8 ([d5ed79c](https://github.com/Yggdrasil-Labs/valhalla-user/commit/d5ed79c1e4a909f3f13c91e8873f03aa15bd3474))
* **deps:** bump actions/setup-java from 4 to 5 ([250a870](https://github.com/Yggdrasil-Labs/valhalla-user/commit/250a870aa544def4e862f96d7d8fe7317e927a53))
* **deps:** bump googleapis/release-please-action from 4.2.0 to 4.4.0 ([6d22813](https://github.com/Yggdrasil-Labs/valhalla-user/commit/6d228139d337a9457b8f3d2639b686306875a5b5))
* **deps:** bump softprops/action-gh-release from 1 to 2 ([3b0a504](https://github.com/Yggdrasil-Labs/valhalla-user/commit/3b0a504c218fdc30681221b000bf2a2e3ce1d416))
* **deps:** bump softprops/action-gh-release from 1 to 2 ([7d3dbf3](https://github.com/Yggdrasil-Labs/valhalla-user/commit/7d3dbf3bd0adfc8a2b93a6def8c660fc6509f5b0))
* **release:** build-verify时401 ([cfcbe3d](https://github.com/Yggdrasil-Labs/valhalla-user/commit/cfcbe3d5da3a570b08e6fc56334f00e72007d995))
* 修改判断逻辑，避免误升级 ([9170294](https://github.com/Yggdrasil-Labs/valhalla-user/commit/91702946efa5b045eb63352bb58e680628ef1500))
* 同步midgard模板仓库的工作流优化 ([2d4eaa3](https://github.com/Yggdrasil-Labs/valhalla-user/commit/2d4eaa3e9c6217084ec8060e257e1801bdb6f3db))


### 🔧 Miscellaneous Chores

* **deps:** bump jakarta.validation:jakarta.validation-api ([7358678](https://github.com/Yggdrasil-Labs/valhalla-user/commit/7358678631515a9b2e89f5ab72719a67d0ac5baf))
* **main:** release 1.0.0 ([36899cb](https://github.com/Yggdrasil-Labs/valhalla-user/commit/36899cbd7226fb27878d013810ed18d67a704081))
* 升级mimir-boot到1.5.0 ([8f8b311](https://github.com/Yggdrasil-Labs/valhalla-user/commit/8f8b311cae20f0dcda38fa335e12d58af41d2279))


### 💄 Code Style

* code format ([e3a17a0](https://github.com/Yggdrasil-Labs/valhalla-user/commit/e3a17a0f81c4b9fd58a1f20370a6e16b9412a557))

## 1.0.0 (2025-12-21)


### ✨ Features

* infra层新增DO实体 ([59aa563](https://github.com/Yggdrasil-Labs/valhalla-user/commit/59aa5637bc2c7a916cdcec9fa56f3e74bbeb4302))
* 修改逻辑删除字段的类型与用法 ([b3f9f76](https://github.com/Yggdrasil-Labs/valhalla-user/commit/b3f9f76133fa7c3028cd5a0497486e115d533a98))
* 完成服务主体功能 ([004235f](https://github.com/Yggdrasil-Labs/valhalla-user/commit/004235fccd50b58d7f578279ea06a0dd3e2e8f5c))
* 数据库初始化脚本 ([20283db](https://github.com/Yggdrasil-Labs/valhalla-user/commit/20283dbd9a77f6bf845daf3e518ecf6a5d793fa4))


### 🐛 Bug Fixes

* 关联表增加自增主键 ([f0c6599](https://github.com/Yggdrasil-Labs/valhalla-user/commit/f0c6599b5c09c61b504e63eee2d76649ad029eb3))
* 去掉冗余的id参数 ([9c7d356](https://github.com/Yggdrasil-Labs/valhalla-user/commit/9c7d35600b0cf49df099eb686e1d5a0a86073119))
* 统一使用雪花id，使用String类型返回给外部 ([9d60dc6](https://github.com/Yggdrasil-Labs/valhalla-user/commit/9d60dc6a722966cdcf3deb4bdedd0433fa447e75))


### 📝 Documentation

* 优化数据库设计文档 ([f89ab13](https://github.com/Yggdrasil-Labs/valhalla-user/commit/f89ab1369ca9b0aba57bd536a045ff19d0ffcc25))
* 完善README ([74294d7](https://github.com/Yggdrasil-Labs/valhalla-user/commit/74294d773ded4ab1a0b288a972c18ecd89a0555f))
* 新增数据库设计文档 ([e2f32ad](https://github.com/Yggdrasil-Labs/valhalla-user/commit/e2f32ad460a32d9ad8d6853292997dd6e7c7b45d))


### ♻️ Code Refactoring

* 清理不需要的代码 ([33a7046](https://github.com/Yggdrasil-Labs/valhalla-user/commit/33a70463c7c5c78edb2c3c861f5f55bff3a3c280))
* 相关文档重命名valhalla-user ([6a2f099](https://github.com/Yggdrasil-Labs/valhalla-user/commit/6a2f09944139db42632490511ed5817bd26b7156))


### 👷 Continuous Integration

* **deps:** bump actions-ecosystem/action-add-labels from 1.1.0 to 1.1.3 ([6928f59](https://github.com/Yggdrasil-Labs/valhalla-user/commit/6928f59501131030bab52851d2302d3879c94425))
* **deps:** bump actions/github-script from 7 to 8 ([d5ed79c](https://github.com/Yggdrasil-Labs/valhalla-user/commit/d5ed79c1e4a909f3f13c91e8873f03aa15bd3474))
* **deps:** bump actions/setup-java from 4 to 5 ([250a870](https://github.com/Yggdrasil-Labs/valhalla-user/commit/250a870aa544def4e862f96d7d8fe7317e927a53))
* **deps:** bump googleapis/release-please-action from 4.2.0 to 4.4.0 ([6d22813](https://github.com/Yggdrasil-Labs/valhalla-user/commit/6d228139d337a9457b8f3d2639b686306875a5b5))
* **deps:** bump softprops/action-gh-release from 1 to 2 ([3b0a504](https://github.com/Yggdrasil-Labs/valhalla-user/commit/3b0a504c218fdc30681221b000bf2a2e3ce1d416))
* **deps:** bump softprops/action-gh-release from 1 to 2 ([7d3dbf3](https://github.com/Yggdrasil-Labs/valhalla-user/commit/7d3dbf3bd0adfc8a2b93a6def8c660fc6509f5b0))
* 修改判断逻辑，避免误升级 ([9170294](https://github.com/Yggdrasil-Labs/valhalla-user/commit/91702946efa5b045eb63352bb58e680628ef1500))
* 同步midgard模板仓库的工作流优化 ([2d4eaa3](https://github.com/Yggdrasil-Labs/valhalla-user/commit/2d4eaa3e9c6217084ec8060e257e1801bdb6f3db))


### 🔧 Miscellaneous Chores

* **deps:** bump jakarta.validation:jakarta.validation-api ([7358678](https://github.com/Yggdrasil-Labs/valhalla-user/commit/7358678631515a9b2e89f5ab72719a67d0ac5baf))
* 升级mimir-boot到1.5.0 ([8f8b311](https://github.com/Yggdrasil-Labs/valhalla-user/commit/8f8b311cae20f0dcda38fa335e12d58af41d2279))


### 💄 Code Style

* code format ([e3a17a0](https://github.com/Yggdrasil-Labs/valhalla-user/commit/e3a17a0f81c4b9fd58a1f20370a6e16b9412a557))
