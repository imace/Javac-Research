/* ************************************************************************
 * Visitor methods
 *************************************************************************/

    /** Visitor argument: the current environment.
     */
    Env<AttrContext> env;

    /** Visitor argument: the currently expected proto-kind.
     */
    int pkind;

    /** Visitor argument: the currently expected proto-type.
     */
    Type pt;

    /** Visitor result: the computed type.
     */
    Type result;

    /** Visitor method: attribute a tree, catching any completion failure
     *  exceptions. Return the tree's type.
     *
     *  @param tree    The tree to be visited.
     *  @param env     The environment visitor argument.
     *  @param pkind   The protokind visitor argument.
     *  @param pt      The prototype visitor argument.
     */
    Type attribTree(JCTree tree, Env<AttrContext> env, int pkind, Type pt) {
    	DEBUG.P(this,"attribTree(4)");
    	DEBUG.P("tree="+tree);
    	DEBUG.P("tree.tag="+tree.myTreeTag());
    	//DEBUG.P("env="+env);
    	DEBUG.P("pkind="+Kinds.toString(pkind));
    	DEBUG.P("pt="+pt);
    	DEBUG.P("pt.tag="+TypeTags.toString(pt.tag));
    	
        Env<AttrContext> prevEnv = this.env;
        int prevPkind = this.pkind;
        Type prevPt = this.pt;
        try {
            this.env = env;
            this.pkind = pkind;
            this.pt = pt;
            tree.accept(this);
            if (tree == breakTree) //当breakTree==tree==null时
                throw new BreakAttr(env);//是java.lang.RuntimeException的子类
            return result;
        } catch (CompletionFailure ex) {
            tree.type = syms.errType;
            return chk.completionError(tree.pos(), ex);
        } finally {
            this.env = prevEnv;
            this.pkind = prevPkind;
            this.pt = prevPt;

				
				DEBUG.P("pkind="+Kinds.toString(pkind));
				DEBUG.P("tree     ="+tree);
    			DEBUG.P("tree.tag ="+tree.myTreeTag());
			if(tree.type!=null)  {
				DEBUG.P("tree.type          ="+tree.type);
				DEBUG.P("tree.type.tsym     ="+tree.type.tsym);
				if(tree.type.tsym!=null)
					DEBUG.P("tree.type.tsym.type="+tree.type.tsym.type);
			} else 
				DEBUG.P("tree.type=null");
            
            DEBUG.P(0,this,"attribTree(4)");
        }
    }