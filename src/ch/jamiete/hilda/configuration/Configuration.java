/*******************************************************************************
 * Copyright 2017 jamietech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ch.jamiete.hilda.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ch.jamiete.hilda.Hilda;

public class Configuration {
    private File file;
    private JsonObject json;

    public Configuration(File file) {
        this.file = file;
    }

    public JsonObject get() {
        return this.json;
    }

    public void save() {
        try {
            FileUtils.write(file, new Gson().toJson(json), Charset.defaultCharset());
        } catch (IOException e) {
            Hilda.getLogger().log(Level.WARNING, "Encountered an exception when saving config " + file.getName(), e);
        }
    }

    public void load() {
        if (!file.exists()) {
            this.json = new JsonObject();
            return;
        }

        try {
            this.json = new JsonParser().parse(FileUtils.readFileToString(file, Charset.defaultCharset())).getAsJsonObject();
        } catch (IOException e) {
            Hilda.getLogger().log(Level.WARNING, "Encountered an exception while loading configuration " + file.getName(), e);
            this.json = new JsonObject();
        }
    }

}