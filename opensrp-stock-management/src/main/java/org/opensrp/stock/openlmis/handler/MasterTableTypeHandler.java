package org.opensrp.stock.openlmis.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.ektorp.impl.StdObjectMapperFactory;
import org.opensrp.stock.openlmis.domain.MasterTableMetaData;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MasterTableTypeHandler implements TypeHandler<MasterTableMetaData> {

    public static final ObjectMapper mapper = new StdObjectMapperFactory().createObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, MasterTableMetaData parameter, JdbcType jdbcType) throws SQLException {
        try {
            if (parameter != null) {
                String jsonString = mapper.writeValueAsString(parameter);
                PGobject jsonObject = new PGobject();
                jsonObject.setType("jsonb");
                jsonObject.setValue(jsonString);
                ps.setObject(i, jsonObject);
            }
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public MasterTableMetaData getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String jsonString = rs.getString(columnName);
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            return mapper.readValue(jsonString, MasterTableMetaData.class);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public MasterTableMetaData getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String jsonString = rs.getString(columnIndex);
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            return mapper.readValue(jsonString, MasterTableMetaData.class);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }

    @Override
    public MasterTableMetaData getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String jsonString = cs.getString(columnIndex);
            if (StringUtils.isBlank(jsonString)) {
                return null;
            }
            return mapper.readValue(jsonString, MasterTableMetaData.class);
        }
        catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
