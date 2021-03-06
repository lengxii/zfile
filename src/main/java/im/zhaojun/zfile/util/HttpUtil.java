package im.zhaojun.zfile.util;

import im.zhaojun.zfile.exception.PreviewException;
import im.zhaojun.zfile.model.constant.ZFileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhaojun
 */
@Slf4j
public class HttpUtil {

    /**
     * 最大支持文件预览大小: 1M
     */
    public static String getTextContent(String url) {
        RestTemplate restTemplate = SpringContextHolder.getBean("restTemplate");

        if (getRemoteFileSize(url) > (1024 * ZFileConstant.TEXT_MAX_FILE_SIZE_KB)) {
            throw new PreviewException("存储源跨域请求失败, 服务器中转状态, 预览文件超出大小, 最大支持 1M");
        }

        String result = restTemplate.getForObject(url, String.class);
        return result == null ? "" : result;
    }

    /**
     * 获取远程文件大小
     */
    public static Long getRemoteFileSize(String url) {
        HttpHeaders httpHeaders = new RestTemplate().headForHeaders(url);
        return httpHeaders.getContentLength();
    }

}
