package com.azoft.energosbyt.prebilling.converter.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ReferenceQueryService {

    private static final String REGION_QUERY =
            "SELECT out_value FROM get_one_references('R_STATE','CCB','ID','CCB','DESC')\n" +
                    "WHERE in_value = :state;";


    private static final String SYSTEM_QUERY =
            "select out_value from get_one_references('SYSTEMS','ISH','id','PB','id')\n" +
                    "WHERE in_value = :systemName;";

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    public String getInformSystemCode(String system) {

        if (Strings.isBlank(system)) {
            return null;
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("systemName", system);
        List<String> queryResult = jdbcTemplate.queryForList(SYSTEM_QUERY, namedParameters, String.class);

        if (queryResult.isEmpty()) {
            return null;
        }

        return queryResult.get(0);
    }

    public String getRegion(String state) {

        if (Strings.isBlank(state)) {
            return null;
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("state", state);
        List<String> queryResult = jdbcTemplate.queryForList(REGION_QUERY, namedParameters, String.class);

        if (queryResult.isEmpty()) {
            return null;
        }

        return queryResult.stream()
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }
}
