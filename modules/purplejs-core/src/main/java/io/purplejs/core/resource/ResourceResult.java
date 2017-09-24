package io.purplejs.core.resource;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

public final class ResourceResult
    implements Supplier<ResourcePath>
{
    private ResourcePath found;

    private final List<ResourcePath> scanned;

    public ResourceResult()
    {
        this.found = null;
        this.scanned = Lists.newArrayList();
    }

    public ResourcePath getFound()
    {
        return this.found;
    }

    public List<ResourcePath> getScanned()
    {
        return this.scanned;
    }

    public void setFound( final ResourcePath found )
    {
        this.found = found;
    }

    public void addScanned( final ResourcePath path )
    {
        this.scanned.add( path );
    }

    @Override
    public ResourcePath get()
    {
        return this.found;
    }
}
