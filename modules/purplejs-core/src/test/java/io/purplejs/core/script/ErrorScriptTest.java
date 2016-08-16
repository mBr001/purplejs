package io.purplejs.core.script;

import org.junit.Test;

import io.purplejs.core.exception.ProblemException;
import io.purplejs.core.resource.ResourcePath;
import io.purplejs.core.value.ScriptExports;

import static org.junit.Assert.*;

public class ErrorScriptTest
    extends AbstractScriptTest
{
    @Test
    public void compileError()
    {
        final ResourcePath path = ResourcePath.from( "/error/compile-error.js" );

        try
        {
            run( path );
            fail( "Should throw ProblemException" );
        }
        catch ( final ProblemException e )
        {
            assertEquals( 1, e.getLineNumber() );
            assertEquals( path, e.getPath() );
        }
    }

    @Test
    public void runtimeError()
    {
        final ResourcePath path = ResourcePath.from( "/error/runtime-error.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );

        try
        {
            exports.executeMethod( "hello" );
            fail( "Should throw ProblemException" );
        }
        catch ( final ProblemException e )
        {
            assertEquals( 2, e.getLineNumber() );
            assertEquals( path, e.getPath() );
        }
    }

    @Test
    public void runtimeErrorInRequire()
    {
        final ResourcePath path = ResourcePath.from( "/error/runtime-error-require.js" );
        final ScriptExports exports = run( path );

        assertNotNull( exports );

        try
        {
            exports.executeMethod( "hello" );
            fail( "Should throw ProblemException" );
        }
        catch ( final ProblemException e )
        {
            assertEquals( 1, e.getLineNumber() );
            assertEquals( ResourcePath.from( "/error/compile-error.js" ), e.getPath() );
        }
    }
}