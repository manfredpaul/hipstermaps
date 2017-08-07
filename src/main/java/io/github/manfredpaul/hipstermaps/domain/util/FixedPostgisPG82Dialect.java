package io.github.manfredpaul.hipstermaps.domain.util;

import java.sql.Types;
import java.util.Map;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.spatial.SpatialDialect;
import org.hibernate.spatial.SpatialFunction;
import org.hibernate.spatial.dialect.postgis.PGGeometryTypeDescriptor;
import org.hibernate.spatial.dialect.postgis.PostgisPG82Dialect;
import org.hibernate.spatial.dialect.postgis.PostgisSupport;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import io.github.jhipster.domain.util.FixedPostgreSQL82Dialect;


public class FixedPostgisPG82Dialect extends PostgisPG82Dialect implements SpatialDialect {

	public FixedPostgisPG82Dialect() {
        super();
        registerColumnType(Types.BLOB, "bytea");
	}

    @Override
    public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        if (sqlTypeDescriptor.getSqlType() == java.sql.Types.BLOB) {
            return BinaryTypeDescriptor.INSTANCE;
        }
        return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
    }
}
