package com.room302.shop.utils;


import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FastDFS {
    private final static String FASTDFS_CONFIG = "fastdfs_client.conf";
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageClient storageClient = null;
    public FastDFS() throws IOException, MyException {
            ClientGlobal.init(FASTDFS_CONFIG);
        System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
    }

    /**
     * 避免文件冲突，每次实例化一个StorageClient
     * */
    public  StorageClient getStorageClient() throws IOException {

            if(trackerClient==null)
                trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            if(trackerServer==null)
                trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }
            if(storageClient == null)
                storageClient = new StorageClient(trackerServer, storageServer);
            return storageClient;

    }

    /**
     * 向FASTDFS上传文件
     * */
    synchronized	public String uploadByFastDFS(MultipartFile file) throws IOException, MyException {

            StorageClient storageClient = this.getStorageClient();
            String extName = getFileType(file.getOriginalFilename());
            String r []= storageClient.upload_file(file.getBytes(), extName,null);
            if(r!=null)
                return r[0]+"/"+r[1];
            else
                return null;

    }

    /**
     * 重FASTDFS上下载文件，得到byte数组
     * */
    synchronized public byte[] downloadByFastDFS(String fullPath) throws IOException, MyException {

            StorageClient storageClient = this.getStorageClient();
            PathInfo storePath = praseFromUrl(fullPath);
            byte[] buffer = storageClient.download_file(storePath.getGroupName(), storePath.getPath());
            return buffer;


    }
    /**
     * 解析文件路径
     */
    public PathInfo praseFromUrl(String filePath) {
        int pos = filePath.indexOf("group");
        String groupAndPath = filePath.substring(pos);
        pos = groupAndPath.indexOf("/");
        String group = groupAndPath.substring(0, pos);
        String path = groupAndPath.substring(pos + 1);
        PathInfo r = new PathInfo(group, path);
        return r;
    }

    public static String getFileType(String filePath){
        int index = filePath.lastIndexOf(".");
        String res = filePath.substring(index+1);
        return res;
    }

    public static String getFileType(File file){
        return getFileType(file.getAbsolutePath());
    }



    public Map<String,MultipartFile> getMapIsNull(Map<String,MultipartFile> map){
        Map<String,MultipartFile> remap=new HashMap<String, MultipartFile>();
        for(String item:map.keySet()){
            if(!map.get(item).isEmpty()){
                remap.put(item, map.get(item));
            }
        }
        return remap;
    }

    /**
     * 封裝fastDfs文件路徑信息
     * */
    class PathInfo{
        private String groupName;
        private String path;
        public PathInfo(String groupName,String path) {
            this.groupName = groupName;
            this.path = path;
        }
        public String getGroupName() {
            return groupName;
        }
        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
    }
}