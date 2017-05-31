#SearchView搜索框架详解
前段时间看到网上的一个搜索的框架searchview，使用起来比较方便，EditText的各种监听都封装好了的。只要传入一个数组的数据，当EditText输入相关的字符，就会产生显示一个相关的ListView。

效果：

![1](http://i.imgur.com/NWNtNlk.gif)

这个searchview里面已经包含了ListView，传入一个数据源后，它会动态监听数据改变然后显示出符合你要求的数据，我这里是显示包含的数据，你也可以显示开头相同的数据。
这里点击显示出来的ListView的条目是有个回调事件，在搜索框输入内容按系统键盘的搜索也是那个回调事件，不过这个模拟器不显示系统键盘！
#searchview的使用

searchview框架就三个文件和一些资源文件。
最重要的是MaterialSearchView这个类的一些公开方法：
##（一）布局文件中
```
<?xml version="1.0" encoding="utf-8"?>

<FrameLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/toolbar_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            >
	
	//写主页面部分的代码
      
    </LinearLayout>
    <com.example.searchview.widget.search.MaterialSearchView
            android:id="@+id/search_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
</FrameLayout>



```
使用的是帧布局，显示搜索框的时候覆盖在页面上，通过代码或点击搜索框左边的回退键或空白的地方搜索界面消失。

##（二）java代码部分主要代码
```
//自定义View对象
MaterialSearchView search_view;

//实例化
 search_view = (MaterialSearchView) findViewById(R.id.search_view);

	//是否支持声音的输入（新的手机会自带有这个功能，不过我的好像不行，会自动崩掉）
 search_view.setVoiceSearch(false); //or true  
  ，
 //设置点击Item是否要回调搜索方法，设置为true后，单击ListView的条目，search_view隐藏。实现数据的搜索
search_view.setSubmitOnClick(true); 

        search_view.setEllipsize(true);   //搜索框的ListView中的Item条目是否是单显示
//把集合数据转换为数组数据（其实也可以进入框架里面进行简单修改，或多增一个公开方法）
        String[] array = list.toArray(new String[list.size()]);

        search_view.setSuggestions(array);//把数据传给搜索框


   //搜索框中EditText数据的监听（在自定义类中已经做了些处理）
        search_view.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            //数据提交时
            //1.点击ListView的Item条目会回调这个方法
            //2.点击系统键盘的搜索/按回车建后回调这个方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MyActivity.this, "你要搜索的是：" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            //文本内容发生改变时，因为框架里面已经做了处理，这里可以不管
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



```

外部的调用(一般在外部设置一个按钮来显示搜索框)：
```
  /**
     * 右上角的输入框的显示和隐藏选择
     *
     * @param view
     */
    public void search(View view) {
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();//隐藏搜索框
        } else {
            search_view.showSearch(true);//显示搜索框
        }
    }
```
其他的公开方法：

```
 search_view.setBackground(Drawable background);//设置搜索框的背景
 search_view.setBackgroundColor(int color)；//设置搜索框的背景颜色
search_view.setTextColor(int color)；//设置输入框中的字体的颜色
search_view.setHintTextColor(int color)；//设置EditText默认显示的提示字体颜色
search_view.setHint(CharSequence hint) ；//设置EditText默认显示的文字
search_view.setBackIcon(Drawable drawable)；//设置关闭搜索框的图标
search_view.setSuggestionIcon(Drawable drawable)；设置搜索框中显示的ListView的Item左边的图标
search_view.setInputType(int inputType) ；//设置EditText中输入的类型限制
search_view.setSuggestionBackground(Drawable background)；//设置搜索框中的ListView的背景


```
其实很多功能如果自己需要是可以往里面添加的！框架里面的Adapter也可以改成自己想要的样式。