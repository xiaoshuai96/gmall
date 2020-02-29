package uploadUtils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

//图片上传的工具类
public class PmsUploadUtil {
    public static String uploadImage(MultipartFile multipartFile) {
        String url = "http://192.168.183.128";
        String[] jpgs = null;
        try {
            //上传图片至服务器
            ClientGlobal.init(PmsUploadUtil.class.getResource("/tracker.conf").getPath());
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getTrackerServer();
            //通过tracker获得一个Storage连接客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //获得上传图片的二进制对象
            byte[] bytes = multipartFile.getBytes();
            //图片全名包含后缀
            String originalFilename = multipartFile.getOriginalFilename();
            //获得后缀名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            jpgs = storageClient.upload_file(bytes, extName, null);
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
        }
        for (String s : jpgs) {
            url = url + "/" + s;
        }
        System.out.println(url);
        return url;
    }
}
