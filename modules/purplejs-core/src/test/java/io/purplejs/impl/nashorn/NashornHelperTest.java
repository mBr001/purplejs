package io.purplejs.impl.nashorn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.script.ScriptEngine;

import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptRuntime;

import static org.junit.Assert.*;

public class NashornHelperTest
{
    private ScriptEngine engine;

    @Before
    public void setUp()
    {
        this.engine = createEngine();
    }

    private ScriptEngine createEngine()
    {
        final ClassLoader classLoader = getClass().getClassLoader();
        return NashornHelper.newScriptEngine( classLoader );
    }

    @Test
    public void newScriptEngine()
    {
        final ScriptEngine engine1 = createEngine();
        assertNotNull( engine1 );

        final ScriptEngine engine2 = createEngine();
        assertNotNull( engine2 );

        assertNotSame( engine1, engine2 );
    }

    @Test
    public void isUndefined()
    {
        assertFalse( NashornHelper.isUndefined( 11 ) );
        assertTrue( NashornHelper.isUndefined( null ) );
        assertTrue( NashornHelper.isUndefined( ScriptRuntime.UNDEFINED ) );
    }

    @Test
    public void isDateType()
        throws Exception
    {
        final Object value1 = execute( "var result = {}; result;" );
        assertFalse( NashornHelper.isDateType( value1 ) );

        final Object value2 = execute( "var result = new Date(); result;" );
        assertTrue( NashornHelper.isDateType( value2 ) );
    }

    @Test
    public void toDate()
        throws Exception
    {
        final ScriptObjectMirror value =
            (ScriptObjectMirror) execute( "var result = new Date(Date.parse('1995-11-12T22:24:25Z')); result;" );
        final Date date = NashornHelper.toDate( value );

        final SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
        format.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

        assertEquals( "1995-11-12T22:24:25+0000", format.format( date ) );
    }

    @Test
    public void isArrayType()
        throws Exception
    {
        final Object value1 = execute( "var result = 11; result;" );
        assertFalse( NashornHelper.isArrayType( value1 ) );

        final Object value2 = execute( "var result = {}; result;" );
        assertFalse( NashornHelper.isArrayType( value2 ) );

        final Object value3 = execute( "var result = []; result;" );
        assertTrue( NashornHelper.isArrayType( value3 ) );
    }

    private Object execute( final String script )
        throws Exception
    {
        return this.engine.eval( script );
    }
}
