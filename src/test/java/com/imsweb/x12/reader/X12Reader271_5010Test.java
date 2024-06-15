package com.imsweb.x12.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
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

		
		URL url = this.getClass().getResource("/x270_271/x271_dependent.txt");
		File x12File = new File(url.getFile());
		
		String x12Text = FileUtils.readFileToString(x12File, StandardCharsets.UTF_8).trim();
        X12Reader reader = new X12Reader(FileType.ANSI271_5010_X279, x12File);

        System.out.println("Errors=" + reader.getErrors());
        System.out.println("Fatal Errors=" + reader.getFatalErrors());
        
        List<Loop> loops = reader.getLoops();
        assertEquals( 1, loops.size());

        String gen271Str = new X12Writer(reader).toX12String(LineBreak.CRLF).trim();
        
        String[] origStrArray = x12Text.split("\\n");
        String[] genStrArray = gen271Str.split("\\n");
        assertEquals(origStrArray.length, genStrArray.length, "The Generated length is different from the origin file");
        
        if (origStrArray.length == genStrArray.length) {
        	for (int i = 0; i < origStrArray.length; i++) {
        		assertEquals(origStrArray[i].trim(), genStrArray[i].trim(), "Line #" + i + ": String Diff: ORIG=" + origStrArray[i] + ", GEN=" + genStrArray[i]);
        	}
        }
        
        
        assertTrue(reader.getFatalErrors().isEmpty() && reader.getErrors().isEmpty());
    }
}
