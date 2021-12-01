[TOC]

Design for a simple rpc framework 

# 整体架构
```

```

--- design-

## Serializer 序列化

## NameService 服务注册
## Transport 数据传输
核心设计如下：

Command 抽象请求相应数据：

```
public class Command { 
    protected Header header; // Command 相关信息
    private byte [] payload; // 传输数据
}
```