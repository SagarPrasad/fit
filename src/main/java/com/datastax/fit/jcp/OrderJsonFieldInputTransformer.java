/*
 * Copyright (c) 2016 JCPenney Co. All rights reserved.
 *
 */

package com.datastax.fit.jcp;

import com.datastax.bdp.search.solr.FieldInputTransformer;
import org.apache.lucene.document.Document;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class OrderJsonFieldInputTransformer extends FieldInputTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderJsonFieldInputTransformer.class);

  /**
   * Indicates the columns which needs to be broken
   * @param field
   * @return
   */
    @Override
    public boolean evaluate(String field) {
      LOGGER.info("field evaluation : {}", field);
      return field.equals("jsonfield");
    }

    @Override
    public void addFieldToDocument(SolrCore core, IndexSchema schema, String key, Document doc, SchemaField fieldInfo,
                                   String fieldValue, DocumentHelper helper)
    {
        try {
            LOGGER.info("JsonFieldInputTransformer called for fieldValue={} schema={} fieldInfo={} key={}",
                    fieldValue,  schema.getSchemaName(), fieldInfo.toString(), key);
            // including the orignal field as well. and pass to get the column to make it generic
            helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, fieldInfo, fieldValue);
            Map<String, List<String>> stringMap = OrderJsonMapConverter.createMapList(fieldValue);
            for (Map.Entry<String, List<String>> entry : stringMap.entrySet()) {
                SchemaField mapField = core.getLatestSchema().getField("order_" + entry.getKey());
                for (String value : entry.getValue()){
                  if (null != value) {
                    helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, mapField, value);
                  }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
