package com.ly.design.message.bus.design1.apply;



import com.ly.design.message.bus.design1.EventBus;

import java.nio.file.*;

public class DirectoryTargetMonitor {
    private WatchService watchService;
    private final EventBus eventBus;
    private final Path path;
    private volatile boolean start = false;

    public DirectoryTargetMonitor(final EventBus eventBus,final String targetPath ) {
        this(eventBus, targetPath,"");
    }

    //构造Monitor的时候需要传入EventBus以及需要监控的目录
    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }

    public void startMonitor() throws Exception {
        this.watchService = FileSystems.getDefault().newWatchService();
        //为路径注册感兴趣的事件
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE);
        System.out.printf("The directory [%s] is monitoring... \n", path);
        this.start = true;
        while (start)
        {
            WatchKey watchKey = null;
            try
            {
                //当有事件发生时会返回对应的WatchKey
                watchKey = watchService.take();
                watchKey.pollEvents().forEach(event ->
                {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    //提交FileChangeEvent 到EventBus
                    eventBus.post(new FileChangeEvent(child, kind));
                });
            }catch (Exception e) {
                e.printStackTrace();
                this.start = false;
            }finally {
                if (watchKey != null) {
                    watchKey.reset();
                }

            }
        }
    }

    public void stopMonitor() throws Exception
    {
        System.out.printf("The Directory [%s] monitor will be stoped... \n", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        System.out.printf("The Directory [%s] monitor will be stoped doen.\n", path);
    }
}