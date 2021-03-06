package net.goldtreeservers.worldguardextraflags.flags.handlers;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import net.goldtreeservers.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import net.goldtreeservers.worldguardextraflags.utils.FlagUtils;
import net.goldtreeservers.worldguardextraflags.utils.WorldGuardUtils;

public class WorldEditFlag extends AbstractDelegateExtent
{
	private final Actor actor;
	
	public WorldEditFlag(Extent extent, Actor actor)
	{
		super(extent);
		this.actor = actor;
	}
	
    @Override
    public boolean setBlock(Vector location, BaseBlock block) throws WorldEditException
    {
    	Player player = WorldGuardExtraFlagsPlugin.getPlugin().getServer().getPlayer(this.actor.getUniqueId());
		if (WorldGuardUtils.hasBypass(player))
    	{
    		return super.setBlock(location, block);
    	}
    	else
    	{
    		if (WorldGuardExtraFlagsPlugin.getWorldGuardPlugin().getRegionContainer().createQuery().getApplicableRegions(BukkitUtil.toLocation(player.getWorld(), location)).queryValue(WorldGuardUtils.wrapPlayer(player), FlagUtils.WORLDEDIT) != State.DENY)
    		{
    			return super.setBlock(location, block);
    		}
    		else
    		{
    			return false;
    		}
    	}
    }
}
