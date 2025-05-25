package ma.zyn.app.zynerator.transverse.cloud;

import io.minio.errors.MinioException;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface MinIOService {
    MinIOInfos uploadToMinio(MultipartFile file, String bucket);

    boolean bucketExists(String name);

    List<MinIOInfos> uploadMultipleToMinio(List<MultipartFile> files, String bucket);

    int saveBucket(String bucket);

    List<String> getAllDocumentsNames(String bucket) throws MinioException;

    byte[] loadDocument(String bucket, String documentName) throws MinioException;

    int setObjectTags(String bucketName, String objectName, Map<String, String> tags) throws MinioException;

    int deleteMultiple(List<String> fileUrls, String bucketName);

    int deleteMultiple(List<String> fileUrls);

    MinIOInfos uploadToMinioUsingByte(List<byte[]> fileBytesList, List<String> fileNames, String bucket);

    MinIOInfos uploadToMinioUsingByte(List<byte[]> fileBytesList, List<String> fileNames);
}
