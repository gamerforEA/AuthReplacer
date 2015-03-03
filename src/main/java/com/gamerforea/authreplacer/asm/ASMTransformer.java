package com.gamerforea.authreplacer.asm;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.gamerforea.authreplacer.loader.CoreMod;

public class ASMTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		if (transformedName.equals("net.minecraft.client.multiplayer.NetClientHandler")) return this.patch(basicClass, 0);
		if (transformedName.equals("net.minecraft.network.ThreadLoginVerifier")) return this.patch(basicClass, 1);

		return basicClass;
	}

	private byte[] patch(byte[] basicClass, int type)
	{
		ClassNode cNode = new ClassNode();
		ClassReader cReader = new ClassReader(basicClass);
		cReader.accept(cNode, 0);

		String urlOld = null;
		String urlNew = null;

		if (type == 0)
		{
			urlOld = "http://session.minecraft.net/game/joinserver.jsp?user=";
			urlNew = CoreMod.joinServerURL;
		}
		else
		{
			urlOld = "http://session.minecraft.net/game/checkserver.jsp?user=";
			urlNew = CoreMod.checkServerURL;
		}

		for (MethodNode mNode : cNode.methods)
		{
			for (AbstractInsnNode abstractInsn : mNode.instructions.toArray())
			{
				if (!(abstractInsn instanceof LdcInsnNode)) continue;
				LdcInsnNode insn = (LdcInsnNode) abstractInsn;
				if (!(insn.cst instanceof String)) continue;
				String s = insn.cst.toString();
				if (s.equals(urlOld)) insn.cst = urlNew;
			}
		}

		ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cNode.accept(cWriter);
		return cWriter.toByteArray();
	}
}