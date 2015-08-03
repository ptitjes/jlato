package org.jlato.unit.util;

import org.jlato.tree.*;
import org.jlato.tree.decl.*;
import org.jlato.tree.expr.*;
import org.jlato.tree.name.*;
import org.jlato.tree.stmt.*;
import org.jlato.tree.type.*;

import static org.jlato.tree.TreeFactory.*;

import org.jlato.util.Function0;
import org.jlato.util.Function2;

import java.util.Random;

public class Arbitrary {

	private static final int MAX_LIST_SIZE = 10;
	private static final int MAX_STRING_SIZE = 6;
	private static final int MAX_QUALIFIER_SIZE = 3;
	private static final int MAX_BLOCK_SIZE = 20;

	private final boolean enableFalseNoneOrEmpty;
	private Random random = new Random(753190246);

	public Arbitrary() {
		this(false);
	}

	public Arbitrary(boolean enableFalseNoneOrEmpty) {
		this.enableFalseNoneOrEmpty = enableFalseNoneOrEmpty;
	}

	private int choice(int max) {
		return random.nextInt(max);
	}

	private int nonNullChoice(int max) {
		return random.nextInt(max - 1) + 1;
	}

	private <T> T choiceIn(T[] choices) {
		return choices[choice(choices.length)];
	}

	public boolean arbitraryBoolean() {
		return enableFalseNoneOrEmpty ? random.nextBoolean() : true;
	}

	public String arbitraryString() {
		StringBuilder builder = new StringBuilder();
		final int size = nonNullChoice(MAX_STRING_SIZE);
		for (int i = 0; i < size; i++) {
			builder.append((char) ('a' + choice(26 - 1)));
		}
		return builder.toString();
	}

	public <T extends Tree> NodeList<T> arbitraryListOf(Function0<T> gen) {
		NodeList<T> list = NodeList.empty();
		final int size = enableFalseNoneOrEmpty ? choice(MAX_LIST_SIZE) : nonNullChoice(MAX_LIST_SIZE);
		for (int i = 0; i < size; i++) {
			list = list.append(gen.apply());
		}
		return list;
	}

	public <T extends Tree> NodeOption<T> arbitraryOptionOf(Function0<T> gen) {
		if (enableFalseNoneOrEmpty) {
			final boolean none = arbitraryBoolean();
			return none ? NodeOption.<T>none() : NodeOption.of(gen.apply());
		} else {
			return NodeOption.of(gen.apply());
		}
	}

	public <T extends Tree> T anyArbitraryIn(Function0<T>... gens) {
		return choiceIn(gens).apply();
	}

	public Name arbitraryName() {
		return new Name(arbitraryString());
	}

	public UnaryOp arbitraryUnaryOp() {
		return choiceIn(UnaryOp.values());
	}

	public BinaryOp arbitraryBinaryOp() {
		return choiceIn(BinaryOp.values());
	}

	public AssignOp arbitraryAssignOp() {
		return choiceIn(AssignOp.values());
	}

	public Primitive arbitraryPrimitive() {
		return choiceIn(Primitive.values());
	}

	public QualifiedName arbitraryQualifiedName() {
		final int size = nonNullChoice(MAX_QUALIFIER_SIZE);
		NodeList<Name> list = NodeList.empty();
		for (int i = 0; i < size; i++) {
			list = list.append(arbitraryName());
		}
		return list.foldLeft(null, new Function2<QualifiedName, Name, QualifiedName>() {
			@Override
			public QualifiedName apply(QualifiedName qualifiedName, Name name) {
				return new QualifiedName(NodeOption.of(qualifiedName), name);
			}
		});
	}

	public FormalParameter arbitraryFormalParameter() {
		return new FormalParameter(arbitraryListExtendedModifier(), arbitraryType(), arbitraryBoolean(), arbitraryVariableDeclaratorId());
	}

	private TypeParameter arbitraryTypeParameter() {
		return new TypeParameter(arbitraryListAnnotationExpr(), arbitraryName(), arbitraryListType());
	}

	private AnnotationExpr arbitraryAnnotationExpr() {
		return new MarkerAnnotationExpr(arbitraryQualifiedName());
	}

	public LocalVariableDecl arbitraryLocalVariableDecl() {
		return new LocalVariableDecl(arbitraryListExtendedModifier(), arbitraryType(), arbitraryListVariableDeclarator());
	}

	public VariableDeclarationExpr arbitraryVariableDeclarationExpr() {
		return new VariableDeclarationExpr(arbitraryLocalVariableDecl());
	}

	public VariableDeclaratorId arbitraryVariableDeclaratorId() {
		return new VariableDeclaratorId(arbitraryName(), arbitraryListArrayDim());
	}

	public QualifiedType arbitraryQualifiedType() {
		return new QualifiedType(arbitraryListAnnotationExpr(), NodeOption.<QualifiedType>none(), arbitraryName(), arbitraryOptionListType());
	}

	public BlockStmt arbitraryBlockStmt() {
		return new BlockStmt(arbitraryListStmt());
	}

	public PackageDecl arbitraryPackageDecl() {
		return new PackageDecl(arbitraryListAnnotationExpr(), arbitraryQualifiedName());
	}

	public TypeDecl arbitraryTypeDecl() {
		return anyArbitraryIn(new Function0<TypeDecl>() {
			@Override
			public TypeDecl apply() {
				return new EmptyTypeDecl();
			}
		}, new Function0<TypeDecl>() {
			@Override
			public TypeDecl apply() {
				return new ClassDecl(arbitraryListExtendedModifier(), arbitraryName(), arbitraryListTypeParameter(), arbitraryOptionQualifiedType(), arbitraryListQualifiedType(), arbitraryListMemberDecl());
			}
		}, new Function0<TypeDecl>() {
			@Override
			public TypeDecl apply() {
				return new InterfaceDecl(arbitraryListExtendedModifier(), arbitraryName(), arbitraryListTypeParameter(), arbitraryListQualifiedType(), arbitraryListMemberDecl());
			}
		});
	}

	private MemberDecl arbitraryMemberDecl() {
		return anyArbitraryIn(new Function0<MemberDecl>() {
			@Override
			public MemberDecl apply() {
				return new EmptyMemberDecl();
			}
		}, new Function0<MemberDecl>() {
			@Override
			public MemberDecl apply() {
				return new FieldDecl(arbitraryListExtendedModifier(), arbitraryType(), arbitraryListVariableDeclarator());
			}
		}, new Function0<MemberDecl>() {
			@Override
			public MemberDecl apply() {
				return new MethodDecl(arbitraryListExtendedModifier(), arbitraryListTypeParameter(), arbitraryType(), arbitraryName(), arbitraryListFormalParameter(), arbitraryListArrayDim(), arbitraryListQualifiedType(), arbitraryOptionBlockStmt());
			}
		});
	}

	public Stmt arbitraryStmt() {
		return anyArbitraryIn(new Function0<Stmt>() {
			@Override
			public Stmt apply() {
				return new EmptyStmt();
			}
		}, new Function0<Stmt>() {
			@Override
			public Stmt apply() {
				return new ExpressionStmt(arbitraryExpr());
			}
		}, new Function0<Stmt>() {
			@Override
			public Stmt apply() {
				return new ReturnStmt(arbitraryOptionExpr());
			}
		});
	}

	public Expr arbitraryExpr() {
		return anyArbitraryIn(new Function0<Expr>() {
			@Override
			public Expr apply() {
				return new AssignExpr(arbitraryName(), arbitraryAssignOp(), arbitraryExpr());
			}
		}, new Function0<Expr>() {
			@Override
			public Expr apply() {
				return new BinaryExpr(arbitraryExpr(), arbitraryBinaryOp(), arbitraryExpr());
			}
		}, new Function0<Expr>() {
			@Override
			public Expr apply() {
				return literalExpr(arbitraryBoolean());
			}
		}, new Function0<Expr>() {
			@Override
			public Expr apply() {
				return literalExpr(choice(32));
			}
		}, new Function0<Expr>() {
			@Override
			public Expr apply() {
				return literalExpr(arbitraryString());
			}
		});
	}

	public Type arbitraryType() {
		return anyArbitraryIn(new Function0<Type>() {
			@Override
			public Type apply() {
				return new PrimitiveType(arbitraryListAnnotationExpr(), arbitraryPrimitive());
			}
		}, new Function0<Type>() {
			@Override
			public Type apply() {
				return new VoidType();
			}
		}, new Function0<Type>() {
			@Override
			public Type apply() {
				return new UnknownType();
			}
		});
	}

	public ArrayInitializerExpr arbitraryArrayInitializerExpr() {
		return new ArrayInitializerExpr(arbitraryListExpr(), arbitraryBoolean());
	}

	public NodeList<MemberDecl> arbitraryListMemberDecl() {
		return arbitraryListOf(new Function0<MemberDecl>() {
			@Override
			public MemberDecl apply() {
				return arbitraryMemberDecl();
			}
		});
	}

	public NodeList<FormalParameter> arbitraryListFormalParameter() {
		return arbitraryListOf(new Function0<FormalParameter>() {
			@Override
			public FormalParameter apply() {
				return arbitraryFormalParameter();
			}
		});
	}

	public NodeList<TypeParameter> arbitraryListTypeParameter() {
		return arbitraryListOf(new Function0<TypeParameter>() {
			@Override
			public TypeParameter apply() {
				return arbitraryTypeParameter();
			}
		});
	}

	public NodeList<AnnotationExpr> arbitraryListAnnotationExpr() {
		return arbitraryListOf(new Function0<AnnotationExpr>() {
			@Override
			public AnnotationExpr apply() {
				return arbitraryAnnotationExpr();
			}
		});
	}

	public NodeList<Type> arbitraryListType() {
		return arbitraryListOf(new Function0<Type>() {
			@Override
			public Type apply() {
				return arbitraryType();
			}
		});
	}

	public NodeList<ArrayDim> arbitraryListArrayDim() {
		return arbitraryListOf(new Function0<ArrayDim>() {
			@Override
			public ArrayDim apply() {
				return arrayDim();
			}
		});
	}

	public NodeList<ArrayDimExpr> arbitraryListArrayDimExpr() {
		return arbitraryListOf(new Function0<ArrayDimExpr>() {
			@Override
			public ArrayDimExpr apply() {
				return new ArrayDimExpr(arbitraryListAnnotationExpr(), arbitraryExpr());
			}
		});
	}

	public NodeList<VariableDeclarator> arbitraryListVariableDeclarator() {
		return arbitraryListOf(new Function0<VariableDeclarator>() {
			@Override
			public VariableDeclarator apply() {
				return new VariableDeclarator(arbitraryVariableDeclaratorId(), arbitraryOptionExpr());
			}
		});
	}

	public NodeList<SwitchCase> arbitraryListSwitchCase() {
		return arbitraryListOf(new Function0<SwitchCase>() {
			@Override
			public SwitchCase apply() {
				return new SwitchCase(arbitraryOptionExpr(), arbitraryListStmt());
			}
		});
	}

	public NodeList<CatchClause> arbitraryListCatchClause() {
		return arbitraryListOf(new Function0<CatchClause>() {
			@Override
			public CatchClause apply() {
				return new CatchClause(arbitraryFormalParameter(), arbitraryBlockStmt());
			}
		});
	}

	public NodeList<ExtendedModifier> arbitraryListExtendedModifier() {
		return arbitraryListOf(new Function0<ExtendedModifier>() {
			@Override
			public ExtendedModifier apply() {
				boolean annotation = arbitraryBoolean();
				return annotation ? arbitraryAnnotationExpr() : choiceIn(Modifier.values());
			}
		});
	}

	public NodeList<VariableDeclarationExpr> arbitraryListVariableDeclarationExpr() {
		return arbitraryListOf(new Function0<VariableDeclarationExpr>() {
			@Override
			public VariableDeclarationExpr apply() {
				return arbitraryVariableDeclarationExpr();
			}
		});
	}

	public NodeList<TypeDecl> arbitraryListTypeDecl() {
		return arbitraryListOf(new Function0<TypeDecl>() {
			@Override
			public TypeDecl apply() {
				return arbitraryTypeDecl();
			}
		});
	}

	public NodeList<ImportDecl> arbitraryListImportDecl() {
		return arbitraryListOf(new Function0<ImportDecl>() {
			@Override
			public ImportDecl apply() {
				return new ImportDecl(arbitraryQualifiedName(), arbitraryBoolean(), arbitraryBoolean());
			}
		});
	}

	public NodeList<QualifiedType> arbitraryListQualifiedType() {
		return arbitraryListOf(new Function0<QualifiedType>() {
			@Override
			public QualifiedType apply() {
				return arbitraryQualifiedType();
			}
		});
	}

	public NodeList<EnumConstantDecl> arbitraryListEnumConstantDecl() {
		return arbitraryListOf(new Function0<EnumConstantDecl>() {
			@Override
			public EnumConstantDecl apply() {
				return new EnumConstantDecl(arbitraryListExtendedModifier(), arbitraryName(), arbitraryOptionListExpr(), arbitraryOptionListMemberDecl());
			}
		});
	}

	public NodeList<Expr> arbitraryListExpr() {
		return arbitraryListOf(new Function0<Expr>() {
			@Override
			public Expr apply() {
				return arbitraryExpr();
			}
		});
	}

	public NodeList<MemberValuePair> arbitraryListMemberValuePair() {
		return arbitraryListOf(new Function0<MemberValuePair>() {
			@Override
			public MemberValuePair apply() {
				return new MemberValuePair(arbitraryName(), arbitraryAnnotationExpr());
			}
		});
	}

	public NodeList<Stmt> arbitraryListStmt() {
		return arbitraryListOf(new Function0<Stmt>() {
			@Override
			public Stmt apply() {
				return arbitraryStmt();
			}
		});
	}

	public NodeOption<ReferenceType> arbitraryOptionReferenceType() {
		return arbitraryOptionOf(new Function0<ReferenceType>() {
			@Override
			public ReferenceType apply() {
				return arbitraryQualifiedType();
			}
		});
	}

	public NodeOption<QualifiedType> arbitraryOptionQualifiedType() {
		return arbitraryOptionOf(new Function0<QualifiedType>() {
			@Override
			public QualifiedType apply() {
				return arbitraryQualifiedType();
			}
		});
	}

	public NodeOption<Name> arbitraryOptionName() {
		return arbitraryOptionOf(new Function0<Name>() {
			@Override
			public Name apply() {
				return arbitraryName();
			}
		});
	}

	public NodeOption<ArrayInitializerExpr> arbitraryOptionArrayInitializerExpr() {
		return arbitraryOptionOf(new Function0<ArrayInitializerExpr>() {
			@Override
			public ArrayInitializerExpr apply() {
				return arbitraryArrayInitializerExpr();
			}
		});
	}

	public NodeOption<QualifiedName> arbitraryOptionQualifiedName() {
		return arbitraryOptionOf(new Function0<QualifiedName>() {
			@Override
			public QualifiedName apply() {
				return arbitraryQualifiedName();
			}
		});
	}

	public NodeOption<BlockStmt> arbitraryOptionBlockStmt() {
		return arbitraryOptionOf(new Function0<BlockStmt>() {
			@Override
			public BlockStmt apply() {
				return arbitraryBlockStmt();
			}
		});
	}

	public NodeOption<Stmt> arbitraryOptionStmt() {
		return arbitraryOptionOf(new Function0<Stmt>() {
			@Override
			public Stmt apply() {
				return arbitraryStmt();
			}
		});
	}

	public NodeOption<Expr> arbitraryOptionExpr() {
		return arbitraryOptionOf(new Function0<Expr>() {
			@Override
			public Expr apply() {
				return arbitraryExpr();
			}
		});
	}

	public NodeOption<NodeList<Type>> arbitraryOptionListType() {
		return arbitraryOptionOf(new Function0<NodeList<Type>>() {
			@Override
			public NodeList<Type> apply() {
				return arbitraryListType();
			}
		});
	}

	public NodeOption<NodeList<Expr>> arbitraryOptionListExpr() {
		return arbitraryOptionOf(new Function0<NodeList<Expr>>() {
			@Override
			public NodeList<Expr> apply() {
				return arbitraryListExpr();
			}
		});
	}

	public NodeOption<NodeList<MemberDecl>> arbitraryOptionListMemberDecl() {
		return arbitraryOptionOf(new Function0<NodeList<MemberDecl>>() {
			@Override
			public NodeList<MemberDecl> apply() {
				return arbitraryListMemberDecl();
			}
		});
	}

	public NodeEither<Expr, BlockStmt> arbitraryEitherExprBlockStmt() {
		boolean expr = arbitraryBoolean();
		return expr ? NodeEither.<Expr, BlockStmt>left(arbitraryExpr()) : NodeEither.<Expr, BlockStmt>right(arbitraryBlockStmt());
	}
}
