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

import java.math.BigDecimal;
import java.math.BigInteger;

import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONFunction;
import jp.co.sjts.util.json.JSONNull;
import jp.co.sjts.util.json.JSONString;


/**
 * Verifies if a value is a valid JSON value.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
//Changes by JAPAN TOTAL SYSTEM CO.,LTD
//・Changed "package": net.sf.json.processors → jp.co.sjts.util.json.processors
//・Added annotation "@SuppressWarnings"
@SuppressWarnings({"unchecked", "all" })
public final class JsonVerifier {

   /**
    * Verifies if value is a valid JSON value.
    *
    * @param value the value to verify
    */
   public static boolean isValidJsonValue( Object value ) {
      if( JSONNull.getInstance()
            .equals( value ) || value instanceof JSON || value instanceof Boolean
            || value instanceof Byte || value instanceof Short
            || value instanceof Integer || value instanceof Long || value instanceof Double
            || value instanceof BigInteger || value instanceof BigDecimal
            || value instanceof JSONFunction || value instanceof JSONString
            || value instanceof String ){
         return true;
      }
      return false;
   }
}