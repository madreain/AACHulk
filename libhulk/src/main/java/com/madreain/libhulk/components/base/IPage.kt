package com.madreain.libhulk.components.base


/**
 * 页面接口
 * 一个与NetHelper交互的页面应该包括IPageContext(获取context）、IPageInit(加载交互）等功能
 * 可以通过activity、fragment、view等来实现
 */
interface IPage : IPageContext, IPageInit