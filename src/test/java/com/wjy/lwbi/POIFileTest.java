package com.wjy.lwbi;

import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@SpringBootTest
public class POIFileTest {

    @SneakyThrows
    @Test
    public void testPOIFile() {
        InputStream inputStream = new FileInputStream("D:\\自我总结报告.docx");
        XWPFDocument docx = new XWPFDocument(inputStream);
        List<XWPFParagraph> paragraphs = docx.getParagraphs();
        paragraphs.stream().forEach(p -> {
//            p.setFirstLineIndent(480);
            System.out.println(p.getText());
        });
    }

}
