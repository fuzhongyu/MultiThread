# MultiThread

 **此项目供本人学习使用：**
        
   #### 1.安装依赖
       
       本地需要安装jdk、maven,mysql数据库 等 基础工具
       
   #### 2.项目介绍
      
        多线程编程核心技术学习（外练互斥，内修可见）
        
   #### 3.注意事项
    
        junit 对多线程的支持并不是太好，所以这边都用main函数作为启动入口
        
   #### 4.目录说明 
      
           以下为/src/main/java/com/fzy目录下的目录结构
      
      _目录结构：_ 
        
         ├──living_example                  // 实例
         │
         ├──lock                            // 用lock实现线程锁                   
         │   
         ├──normal_method                   // 线程常用方法
         │     │
         │     └──stop_thread                          //停止线程的方法
         │
         ├──single_ton                      // 单例                 
         │   
         ├──supplement                      //
         │   
         ├──test_file                       //流写入文件  
         │   
         ├──thread_communication            // 线程间交互 
         │     │
         │     ├── product_customer                    //生产者，消费者
         │     │
         │     └── thread_join                         // join()方法
         │   
         ├──thread_synchronized             // synchronized线程锁
         │   
         └──timer                           // 定时器