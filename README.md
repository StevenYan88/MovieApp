
#### 电影资讯

1. 电影资讯App是一个纯练手项目，使用了系统API28，全部是基于Androidx包下的。  
2. 使用Kotlin语言开发，使用了Android JetPack中的LiveData、ViewModel、Room等架构组件。    
3. Api来自豆瓣电影，豆瓣电影Api目前是不公开的，小编是在简书上看到有人公开了访问豆瓣电影的Api的API_KEY，如果有侵犯，请联系删除！

#### 项目截图

截图1 | 截图2 | 截图 3 | 截图 4 | 截图5
---|---|---|---|---
![5.jpg](https://upload-images.jianshu.io/upload_images/1472453-c4212eaa55f9afda.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) | ![3.jpg](https://upload-images.jianshu.io/upload_images/1472453-1ac4b1a4adccafb1.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) | ![1.jpg](https://upload-images.jianshu.io/upload_images/1472453-76e5fe8263ed2fd5.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)|![4.jpg](https://upload-images.jianshu.io/upload_images/1472453-7627622eea059121.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) | ![6.jpg](https://upload-images.jianshu.io/upload_images/1472453-5df4bf5e09e4303b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### app主要设计到知识点、技术点：
- Material Design UI设计风格； 
- 使用Kotlin开发，大家想学习和巩固Kotlin，可以参考这个App；
- 使用了Android JetPack中的LiveData、ViewModel、Room等架构组件；  
- Retrofit融合LiveData去请求网络，手写一个LiveDataCallAdapter适配器，为了Retrofit适配LiveData；  
- 自定义RecyclerView支持上拉刷新和下拉加载，并且使用装饰者设计模式添加不同类型头部和底部的View以及动画；
- 自定义一个视频播放器；


**License**  

    Copyright 2019 StevenYan88
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0  
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
