/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

import org.apache.ofbiz.base.util.FileUtil;

ofbizHomeStr = System.getProperty("ofbiz.home");
ofbizHomeStr = ofbizHomeStr + "/runtime/logs/";
File runTimeLogDir = FileUtil.getFile(ofbizHomeStr);
File[] listLogFiles = runTimeLogDir.listFiles(); 
List listLogFileNames = []
for (int i = 0; i < listLogFiles.length; i++) {
    if (listLogFiles[i].isFile()) {
        logFileName = listLogFiles[i].getName();
        if (logFileName.startsWith("ofbiz")) {
            listLogFileNames.add(logFileName);
        }
    }
}
context.listLogFileNames = listLogFileNames;

if (parameters.logFileName) {
    logFileName = ofbizHomeStr + parameters.logFileName;
    List logLines = [];
    File logFile = FileUtil.getFile(logFileName);
    logFile.eachLine { line ->
        type = '';
        if (line.contains(":INFO ] ")) {
            type = 'INFO';
        } else if (line.contains(":WARN ] ")) {
            type = 'WARN';
        } else if (line.contains(":ERROR] ")) {
            type = 'ERROR';
        } else if (line.contains(":DEBUG] ")) {
            type = 'DEBUG';
        }
        if (parameters.searchString) {
            if (line.contains(parameters.searchString)) {
                logLines.add([type: type, line:line]);
            }
        } else {
            logLines.add([type: type, line:line]);
        }
    }
    context.logLines = logLines;
}