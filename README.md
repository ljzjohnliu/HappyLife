# READEME

想要在studio中直接调试java程序，需要修改.idea/gradle.xml
添加<option name="delegatedBuild" value="false" />

<GradleProjectSettings>
        <option name="delegatedBuild" value="false" />
        <option name="testRunner" value="PLATFORM" />
        ...
      </GradleProjectSettings>

## common封装了部分基础功能
  audioRecord目录是封装录音相关功能；
  base目录封装Activity和Fragment基类，主要是抽象几个方法以及把Activity加到管理栈中；
  basemvp目录封装了MVP框架的Activity和Fragment基类；
  connection目录封装了网络状态相关的工具
  endpoint目录是设置网络请求正式环境，发布环境等的baseUrl；
  events目录添加基础的事件类；
  http目录封装了网络相关的基础类，便于拦截请求以及返回处理；
  log目录封装日志相关；
  model目录封装了bean基类；
  utils目录封装常用工具类。

## widget自定义底部导航栏

## calendarview自定义日历控件

## dataroom封装数据库模块

## NetWorkAPI目录封装Retrofit网络实现

## customview自定义View模块

### 遇到问题：无法内容自适应
    view_fab.xml中id是right_side_layout的布局部分，由于right_side_entrance这个id的view某些场景可见，某些场景不可见。
    所以想要right_side_layout内容自适应，这样right_side_entrance可见和不可见的时候可以内容自适应不同不高度。
    但是试下来，并没有达到预期效果！！！
    已经写好了验证问题的代码：在TestCustomAvatarActivity中ChatFabView fab;
    fab.showEntrance(true);右下角的布局ChatFabView显示两个子控件
    fab.showEntrance(false);右下角的布局ChatFabView显示一个子控件