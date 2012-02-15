/*
 * Dropbox4j - Dropbox API Java implementation.
 *
 * Copyright (c) 2012 - Sergey "Frosman" Lukjanov, me@frostman.ru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.frostman.dropbox.util;

import org.junit.Assert;
import org.junit.Test;
import ru.frostman.dropbox.api.util.Files;

import java.io.*;
import java.net.URISyntaxException;

/**
 * @author slukjanov aka Frostman
 */
public class FilesTest {

    @Test
    public void testCreateInputStream() throws URISyntaxException, IOException {
        InputStream is = Files.createInputStream(new File(this.getClass().getResource("/test.txt").toURI()));
        Assert.assertNotNull(is);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Assert.assertEquals("This is a test file", reader.readLine());
    }

    @Test
    public void testCreateOutputStream() throws URISyntaxException, IOException {
        File file = new File(this.getClass().getResource("/test.output.txt").toURI());
        if (file.exists()) {
            Assert.assertTrue(file.delete());
            Assert.assertTrue(file.createNewFile());
        }

        OutputStream os = Files.createOutputStream(file);
        Assert.assertNotNull(os);

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
        writer.println("This is a test file");
        writer.close();

        InputStream is = Files.createInputStream(file);
        Assert.assertNotNull(is);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Assert.assertEquals("This is a test file", reader.readLine());
    }

    @Test
    public void testReadFile() throws URISyntaxException, IOException {
        byte[] bytes = Files.readFile(new File(this.getClass().getResource("/test.txt").toURI()));
        Assert.assertNotNull(bytes);

        Assert.assertEquals("This is a test file\n", new String(bytes));
    }

    @Test
    public void testWriteFile() throws URISyntaxException, IOException {
        File file = new File(this.getClass().getResource("/test.output.txt").toURI());

        Files.writeFile(file, new ByteArrayInputStream("This is a test file".getBytes()));

        byte[] bytes = Files.readFile(file);
        Assert.assertNotNull(bytes);

        Assert.assertEquals("This is a test file", new String(bytes));
    }

    @Test
    public void testCopy() throws IOException {
        byte[] bytes = "This is a test msg".getBytes();

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Files.copy(in, out);

        Assert.assertArrayEquals(bytes, out.toByteArray());
    }

}
