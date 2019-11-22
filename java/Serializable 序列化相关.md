* 第一种：如果你不添加serialVersionUID，生成一个hash值，然后将这个值作为serialVersionUID。

* 第二种情况：
  如果你没有添加serialVersionUID，进行了序列化，而你在反序列化的时候，修改了User对象的结构（添加或删除成员变量，修改成员变量的命名），那么这个时候，就会出现上面的报错。
  
* 第三种情况： 如果你添加了serialVersionUID，进行了序列化，而你在反序列化的时候，修改了User对象的结构（添加或删除成员变量，修改成员变量的命名），那么就不见的报错了，不过反序列化，它只能恢复部分数据，或者恢复不了数据。

* 总结一下：
  序列化的时候，系统会把序列化的类的serialVersionUID写入到序列化文件中（有可能其他地方）,当去反序列化的时候，系统会去检测文件中的serialVersionUID，如果类中的值和文件的值一致，那么就可以反序列化成功，反之失败。
  
  serialVersionUID是可序列化类的一个版本标识，在反序列化的时候使用这个标识的值来判断序列化和反序列化的对象类型是否一致。Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常
  
  ````
  java.io.InvalidClassException: com.inspur.health.sso.common.domain.ViewUser; local class incompatible: 
  stream classdesc serialVersionUID = -1646429081786928572, local class serialVersionUID = -8564557595420428114
   at java.io.ObjectStreamClass.initNonProxy(ObjectStreamClass.java:699)
   at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1885)
   
   一个是默认的1L，比如：“private static final long serialVersionUID = 1L;"；
   
   一个是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段，比如：“private static final long serialVersionUID = -8940196742313994740L;”。
   
   ````
   
  参考文档： https://www.cnblogs.com/aeolian/p/10534705.html
  https://jingyan.baidu.com/article/656db918c36534e381249c83.html