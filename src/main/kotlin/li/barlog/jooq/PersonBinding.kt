package li.barlog.jooq

import li.barlog.model.Person
import org.jooq.Binding
import org.jooq.BindingGetResultSetContext
import org.jooq.BindingGetSQLInputContext
import org.jooq.BindingGetStatementContext
import org.jooq.BindingRegisterContext
import org.jooq.BindingSQLContext
import org.jooq.BindingSetSQLOutputContext
import org.jooq.BindingSetStatementContext
import org.jooq.Converter
import org.jooq.impl.DSL
import java.sql.SQLFeatureNotSupportedException
import java.sql.Types
import java.util.Objects

class PersonBinding : Binding<Any, Person> {
	override fun converter(): Converter<Any, Person> = PersonConverter()

	override fun sql(ctx: BindingSQLContext<Person>) {
		ctx.render().visit(DSL.`val`(ctx.convert(converter()).value())).sql("::jsonb")
	}

	override fun register(ctx: BindingRegisterContext<Person>) {
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR)
	}

	override fun set(ctx: BindingSetStatementContext<Person>) {
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null))
	}

	override fun get(ctx: BindingGetResultSetContext<Person>) {
		ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()))
	}

	override fun get(ctx: BindingGetStatementContext<Person>) {
		ctx.convert(converter()).value(ctx.statement().getString(ctx.index()))
	}

	override fun set(ctx: BindingSetSQLOutputContext<Person>) {
		throw SQLFeatureNotSupportedException()
	}

	override fun get(ctx: BindingGetSQLInputContext<Person>) {
		throw SQLFeatureNotSupportedException()
	}
}
