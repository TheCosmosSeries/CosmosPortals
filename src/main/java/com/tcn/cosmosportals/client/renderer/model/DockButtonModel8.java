package com.tcn.cosmosportals.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class DockButtonModel8 extends Model {
	private static final String STRING = "button";
	
	private final ModelPart root;
	private final ModelPart button;

	public DockButtonModel8() {
		super(RenderType::entitySolid);
		this.root = createLayer().bakeRoot();
		this.button = root.getChild(STRING);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild(STRING, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 3.0F), PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 32, 16);
	}
	
	public ModelPart getButton() {
		return this.button;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, int color) {
		this.root.render(poseStack, vertexBuilder, packedLightIn, packedOverlayIn, color);
	}
}
