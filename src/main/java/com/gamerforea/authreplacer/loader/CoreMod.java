package com.gamerforea.authreplacer.loader;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.6.4")
@IFMLLoadingPlugin.Name(CoreMod.NAME)
@IFMLLoadingPlugin.SortingIndex(1001)
public class CoreMod implements IFMLLoadingPlugin
{
	public static final String MODID = "AuthReplacer";
	public static final String NAME = "AuthReplacer";
	public static final String VERSION = "1.6.4";

	public static boolean isObfuscated = false;
	public static String checkServerURL = "http://site.net/checkserver.php?user=";
	public static String joinServerURL = "http://site.net/joinserver.php?user=";

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "com.gamerforea.authreplacer.asm.ASMTransformer" };
	}

	@Override
	public String getModContainerClass()
	{
		return "com.gamerforea.authreplacer.loader.ModContainer";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}
}