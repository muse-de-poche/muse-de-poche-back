package org.musedepoche.model;

public interface IViews {
	public static interface IViewBasic {}
	
	public static interface IViewDetail {}
	
	public static interface IViewComposer extends IViewBasic {}
	
	public static interface IViewComposerDetail extends IViewComposer , IViewDetail {}
	
	public static interface IViewComposition extends IViewBasic {}

	public static interface IViewCompositionDetail extends IViewComposition, IViewDetail {}
}
