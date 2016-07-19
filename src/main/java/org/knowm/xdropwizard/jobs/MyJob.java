/**
 * Copyright 2015 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2013-2015 Xeiam LLC (http://xeiam.com) and contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.xdropwizard.jobs;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.knowm.sundial.Job;
import org.knowm.sundial.exceptions.JobInterruptException;
import org.knowm.xdropwizard.business.SecurityTrade;
import org.knowm.xdropwizard.constance.commonConstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Iterator;

public class MyJob extends Job implements commonConstance  {

    private final Logger log = LoggerFactory.getLogger(MyJob.class);


    @Override
    public void doRun() throws JobInterruptException {
        log.info("MyJob start");
        for(String sid : SECURITY_MAP.keySet()){
            log.info("do security "+sid+", "+SECURITY_MAP.get(sid));
            JobUtils.getSecurityCompany(sid, log);
        }
    }
}

