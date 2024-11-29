/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.sjts.util.json.processors;

import jp.co.sjts.util.json.JsonConfig;

/**
 * Transforms a java.util.Date property into a JSONObject ideal for JsDate
 * conversion
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
//Changes by JAPAN TOTAL SYSTEM CO.,LTD
//・Changed "package": net.sf.json.processors → jp.co.sjts.util.json.processors
//・Added annotation "@SuppressWarnings"
@SuppressWarnings({"unchecked", "all" })
public class JsDateJsonValueProcessor implements JsonValueProcessor {

   private JsonBeanProcessor processor;

   public JsDateJsonValueProcessor() {
      processor = new JsDateJsonBeanProcessor();
   }

   public Object processArrayValue( Object value, JsonConfig jsonConfig ) {
      return process( value, jsonConfig );
   }

   public Object processObjectValue( String key, Object value, JsonConfig jsonConfig ) {
      return process( value, jsonConfig );
   }

   private Object process( Object value, JsonConfig jsonConfig ) {
      return processor.processBean( value, jsonConfig );
   }
}