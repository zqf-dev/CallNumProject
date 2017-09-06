# CallNumProject
采用Socket+同步锁Synchronized实现类似银行叫号的功能App  
  
一、  
CallNumService.java 叫号后台运行服务,app启动后退出就一直运行在后台接收Socket的叫号信息；  
UdpServerUtil.java   
叫号服务的工具类：主要实现接收到Socket发来的message进行处理后采用合成的声音组合。而达到效果。  
***  
采用Synchronized是防止当多个socket消息同时发送来时声音重叠或者覆盖的问题。
