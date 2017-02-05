package com.utils;

import java.lang.reflect.Modifier;

public class ReflectUtil
{
	public static String getModifierStr(int modifiers)
	{
		return ModifierUtil.getModifierStr(modifiers);
	}

	private static class ModifierUtil
	{
		public static String getModifierStr(int modifiers)
		{
			StringBuilder str = new StringBuilder();

			if(Modifier.isPublic(modifiers))
			{
				str.append("public ");
			}
			if(Modifier.isPrivate(modifiers))
			{
				str.append("private ");
			}
			if(Modifier.isProtected(modifiers))
			{
				str.append("protected ");
			}
			if(Modifier.isAbstract(modifiers))
			{
				str.append("abstract ");
			}
			if(Modifier.isStatic(modifiers))
			{
				str.append("static ");
			}
			if(Modifier.isFinal(modifiers))
			{
				str.append("final ");
			}
			if(Modifier.isSynchronized(modifiers))
			{
				str.append("synchronized ");
			}
			if(Modifier.isNative(modifiers))
			{
				str.append("native ");
			}
			if(Modifier.isStrict(modifiers))
			{
				str.append("strict ");
			}
			if(Modifier.isInterface(modifiers))
			{
				str.append("interface ");
			}
			if(Modifier.isTransient(modifiers))
			{
				str.append("transient ");
			}
			if(Modifier.isVolatile(modifiers))
			{
				str.append("volatile ");
			}


			return str.toString();
		}
	}
}

