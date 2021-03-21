package org.musedepoche.model;

public interface IViews {
	public static interface IViewBasic {}
	
	public static interface IViewDetail {}
	
	public static interface IViewWithComposition {}
	
	public static interface IViewWithComposer {}
	
	public static interface IViewComposer extends IViewBasic {}
	
	public static interface IViewComposerDetail extends IViewComposer , IViewDetail {}

	public static interface IViewCollaboration extends IViewBasic {}
	
	public static interface IViewCollaborationDetail extends IViewCollaboration, IViewDetail, IViewWithComposition, IViewWithComposer {}
	
	public static interface IViewCollaborationByComposer extends IViewCollaboration, IViewWithComposition {}
	
	public static interface IViewCollaborationByComposition extends IViewCollaboration, IViewWithComposer {}
	
	public static interface IViewComposition extends IViewBasic {}

	public static interface IViewCompositionDetail extends IViewComposition, IViewDetail {}
	
	public static interface IViewMessage extends IViewBasic {}

	public static interface IViewMessageDetail extends IViewMessage, IViewDetail, IViewWithComposition, IViewWithComposer {}

	public static interface IViewMessageByComposer extends IViewMessage, IViewWithComposition {}
	
	public static interface IViewMessageByComposition extends IViewMessage, IViewWithComposer {}

}
