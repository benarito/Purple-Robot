package edu.northwestern.cbits.purple_robot_manager.plugins;

import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutputPluginManager extends BroadcastReceiver
{
	private Map<Class<OutputPlugin>, OutputPlugin> _plugins = new HashMap<Class<OutputPlugin>, OutputPlugin>();

	public void onReceive(Context context, Intent intent)
	{
		for (Class<OutputPlugin> pluginClass : OutputPlugin.availablePluginClasses())
		{
			try
			{
				OutputPlugin plugin = this._plugins.get(pluginClass);

				if (plugin == null)
				{
					plugin = pluginClass.newInstance();
					this._plugins.put(pluginClass, plugin);
				}

				plugin.setContext(context);
				plugin.process(intent);
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
}
