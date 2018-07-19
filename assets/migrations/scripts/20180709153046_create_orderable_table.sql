--
--    Copyright 2010-2016 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

-- // Create Orderable Table
-- Migration SQL that makes the change goes here.
CREATE TABLE core.orderable (
    id character varying NOT NULL,
    code character varying,
    full_product_code character varying,
    net_content integer,
    pack_rounding_threshold integer,
    round_to_zero boolean,
    dispensable integer,
    trade_item_id character varying,
    commodity_type_id character varying,
    server_version bigint,
    date_deleted bigint,
    PRIMARY KEY (id)
) WITH (
    OIDS = FALSE
) TABLESPACE core_space;


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE core.orderable;