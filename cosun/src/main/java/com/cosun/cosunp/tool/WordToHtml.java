package com.cosun.cosunp.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * @author:homey Wong
 * @date:2019/5/21 0021 上午 10:48
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class WordToHtml {

    private static Logger logger = LogManager.getLogger(WordToHtml.class);

    /**
     * word07版本(.docx)转html
     * poi:word07在线预览
     */
    public static void word2007ToHtml(String centerPath, String allPath, MultipartFile file, Integer deptId) throws Exception {
        int index = file.getOriginalFilename().lastIndexOf(".");
        String nameOnly = file.getOriginalFilename().substring(0, index);
        String imagePathStr = centerPath + "image/";
        OutputStreamWriter outputStreamWriter = null;
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(allPath));
            XHTMLOptions options = XHTMLOptions.create();
            // 存放图片的文件夹
            options.setExtractor(new FileImageExtractor(new File(imagePathStr)));
            // html中图片的路径
            options.URIResolver(new BasicURIResolver("ftp://admin:FL33771@192.168.0.152/" + deptId + "/image"));
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(centerPath + nameOnly + ".html"), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            xhtmlConverter.convert(document, outputStreamWriter, options);
            outputStreamWriter.close();
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
        }
    }


    public static void DocToHtml(String centerPath, String allPath, MultipartFile file, Integer deptId) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            int index = file.getOriginalFilename().lastIndexOf(".");
            String nameOnly = file.getOriginalFilename().substring(0, index);
            String outPutFile = centerPath + nameOnly + ".html";
            HWPFDocument wordDocument;
            //字节码输出流
            try {
                //根据输入文件路径与名称读取文件流
                InputStream in = new FileInputStream(allPath);
                //把文件流转化为输入wordDom对象
                wordDocument = new HWPFDocument(in);
                //通过反射构建dom创建者工厂
                DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory.newInstance();
                //生成dom创建者
                DocumentBuilder domBuilder = domBuilderFactory.newDocumentBuilder();
                //生成dom对象
                Document dom = domBuilder.newDocument();
                //生成针对Dom对象的转化器
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(dom);
                //转化器重写内部方法
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    public String savePicture(byte[] content,
                                              PictureType pictureType, String suggestedName,
                                              float widthInches, float heightInches) {
                        return "ftp://admin:FL33771@192.168.0.152/" + deptId + "/" + suggestedName.substring(1, suggestedName.length() - 1);
                    }
                });
                //转化器开始转化接收到的dom对象
                wordToHtmlConverter.processDocument(wordDocument);
                //保存文档中的图片
                List<?> pics = wordDocument.getPicturesTable().getAllPictures();
                if (pics != null) {
                    for (int i = 0; i < pics.size(); i++) {
                        Picture pic = (Picture) pics.get(i);
                        try {
                            pic.writeImageContent(new FileOutputStream(centerPath + pic.suggestFullFileName()));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //从加载了输入文件中的转换器中提取DOM节点
                Document htmlDocument = wordToHtmlConverter.getDocument();
                //从提取的DOM节点中获得内容
                DOMSource domSource = new DOMSource(htmlDocument);
                out = new ByteArrayOutputStream();
                //输出流的源头
                StreamResult streamResult = new StreamResult(out);
                //转化工厂生成序列转化器
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                //设置序列化内容格式
                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");

                serializer.transform(domSource, streamResult);
                //生成文件方法
                writeFile(new String(out.toByteArray()), outPutFile);
                out.close();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (TransformerConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (TransformerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (Exception e) {
            out.close();
            throw e;
        }
    }


    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }

}
