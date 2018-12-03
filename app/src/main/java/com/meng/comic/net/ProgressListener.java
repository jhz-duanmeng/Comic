package com.meng.comic.net;

/**
 * Created by Dmeng on 2018/11/20.
 */

public interface ProgressListener {
    /**
     * @param progress 已经下载或上传字节
     * @param total    总字节数
     * @param done     是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
