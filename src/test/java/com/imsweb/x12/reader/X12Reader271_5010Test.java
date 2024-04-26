package com.imsweb.x12.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.imsweb.x12.LineBreak;
import com.imsweb.x12.Loop;
import com.imsweb.x12.reader.X12Reader.FileType;
import com.imsweb.x12.writer.X12Writer;

public class X12Reader271_5010Test {

	@Test
    public void test271_5010() throws Exception {
//		
//        URL url = this.getClass().getResource("/x270_271/x271.5010-01.txt");
//        
//        
//        System.out.println("working dir=" + System.getProperty("user.dir"));
//        
//        try (FileInputStream fis = new FileInputStream(x12File); 
//        		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//        		FileOutputStream fos = new FileOutputStream("test_x271.5010-02.txt");
//        		PrintWriter pw = new PrintWriter(fos)) 
//        {
//        	String s = null;
//        	while ( (s = br.readLine()) != null) {
//        		pw.println(s.trim());
//        	}
//        }
//        catch (Exception e) {
//        	
//        }
//        
		
		URL url = this.getClass().getResource("/x270_271/x271.5010-01.txt");
		File x12File = new File(url.getFile());
		
		String x12Text = FileUtils.readFileToString(x12File, StandardCharsets.UTF_8).trim();
        X12Reader reader = new X12Reader(FileType.ANSI271_5010_X279, x12File);

        List<Loop> loops = reader.getLoops();
        assertEquals( 1, loops.size());

        //System.out.println("x12Text=" + x12Text.trim());
        //System.out.println("==================");
        String gen271Str = new X12Writer(reader).toX12String(LineBreak.CRLF).trim();
        
        //System.out.println("Orig Length: " + x12Text.length() + ", Gen Length: " + gen271Str.length());
        String[] origStrArray = x12Text.split("\\n");
        String[] genStrArray = gen271Str.split("\\n");
        assertEquals(origStrArray.length, genStrArray.length, "The Generated length is different from the origin file");
        
        //System.out.println("Num Of Lines: " + origStrArray.length + ", Gen Num of Lines: " + genStrArray.length);
        if (origStrArray.length == genStrArray.length) {
        	for (int i = 0; i < origStrArray.length; i++) {
        		assertEquals(origStrArray[i].trim(), genStrArray[i].trim(), "Line #" + i + ": String Diff: ORIG=" + origStrArray[i] + ", GEN=" + genStrArray[i]);
        	}
        }
        //System.out.println("rewrite=" + gen271Str);
        
        //assertEquals(x12Text.trim(), new X12Writer(reader).toX12String(LineBreak.CRLF).trim());

        System.out.println("Fatal Errors:" + reader.getFatalErrors());
        System.out.println("Errors:" + reader.getErrors());
        
        assertTrue(reader.getFatalErrors().isEmpty() && reader.getErrors().isEmpty());
    }
}
