<!DOCTYPE html>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="com.iu.amazelocal.models.Inventory"%>
<%@page import="com.iu.amazelocal.models.Rating"%>
<%@page import="com.iu.amazelocal.db.ProductTypeCrud"%>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="robots" content="all,follow">
    <meta name="googlebot" content="index,follow,snippet,archive">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Obaju e-commerce template">
    <meta name="author" content="Ondrej Svestka | ondrejsvestka.cz">
    <meta name="keywords" content="">

    <title>
       Amazingly Local!
    </title>

    <meta name="keywords" content="">

    <link href='http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100' rel='stylesheet' type='text/css'>

    <!-- styles -->
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/owl.carousel.css" rel="stylesheet">
    <link href="css/owl.theme.css" rel="stylesheet">
    <link href="css/img-size.css" rel="stylesheet">

    <!-- theme stylesheet -->
    <link href="css/style.default.css" rel="stylesheet" id="theme-stylesheet">

    <!-- your stylesheet with modifications -->
    <link href="css/custom.css" rel="stylesheet">

    <script src="js/respond.min.js"></script>

    <link rel="shortcut icon" href="favicon.png">

	<%@page import="com.iu.amazelocal.models.ShopCart"%>
	<%@page import="com.iu.amazelocal.db.ShopCartCrud"%>
	<%@page import="java.util.ArrayList"%>

</head>

<body>

    <!-- *** TOPBAR ***
 _________________________________________________________ -->
     <div id="top">
        <div class="container">
            <div class="col-md-6 offer" data-animate="fadeInDown">
                <a href="#" class="btn btn-success btn-sm" data-animate-hover="shake">Offer of the day</a>  <a href="#">Get flat 35% off on orders over $50!</a>
            </div>
            <div class="col-md-6" data-animate="fadeInDown">
                <ul class="menu">
                 	<% if(session.getAttribute("sessionExists")!=null) {%>
                 	
                 	 <li> Welcome <%=session.getAttribute("userName")%><form action="logout" method="get">
                    <input type="submit" value="Logout" class="btn btn-primary"></form> </li>
                 	<%}
						else { %>
                    <li ><a href="loginlanding.html" >Login</a>
                    </li>
                    <li ><a href="register.html">Register</a>
                    </li>
                    <li ><a href="contact.html">Contact</a>
                    </li>
                    <% }  %>
                    
                   
                </ul>
            </div>
        </div>
        <div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
            <div class="modal-dialog modal-sm">

                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="Login">Customer login</h4>
                    </div>
                    <div class="modal-body">
                        <form action="customer-orders.html" method="post">
                            <div class="form-group">
                                <input type="text" class="form-control" id="email-modal" placeholder="email">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="password-modal" placeholder="password">
                            </div>

                            <p class="text-center">
                                <button class="btn btn-primary"><i class="fa fa-sign-in"></i> Log in</button>
                            </p>

                        </form>

                        <p class="text-center text-muted">Not registered yet?</p>
                        <p class="text-center text-muted"><a href="register.html"><strong>Register now</strong></a>! It is easy and done in 1&nbsp;minute and gives you access to special discounts and much more!</p>

                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- *** TOP BAR END *** -->

    <!-- *** NAVBAR ***
 _________________________________________________________ -->

    <div class="navbar navbar-default yamm" role="navigation" id="navbar">
        <div class="container">
            <div class="navbar-header">

                <a class="navbar-brand home" href="index.jsp" data-animate-hover="bounce">
                    <img src="img/al_logo.png" alt="Obaju logo" class="lg" class="hidden-xs">
                    <img src="img/logo-small.png" alt="Obaju logo" class="visible-xs"><span class="sr-only">Amazingly Local!</span>
                </a>
                <div class="navbar-buttons">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navigation">
                        <span class="sr-only">Toggle navigation</span>
                        <i class="fa fa-align-justify"></i>
                    </button>
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#search">
                        <span class="sr-only">Toggle search</span>
                        <i class="fa fa-search"></i>
                    </button>
                    <%  ShopCartCrud cart = new ShopCartCrud();
                                   ArrayList<ShopCart> cartItems = new ArrayList<ShopCart>();
                                   ShopCart orderDetails = new ShopCart();
                                   Long uId = (Long)session.getAttribute("userId");
                                   orderDetails = cart.fetchOrderDetails(uId);
                                   int OrderQuantity = 0;
                                   if(orderDetails != null){
                                   OrderQuantity = orderDetails.getOrderQuantity();}%>
                    <a class="btn btn-default navbar-toggle" href="cart.jsp">
                        <i class="fa fa-shopping-cart"></i>  <span class="hidden-xs"><%=OrderQuantity %> items in cart</span>
                    </a>
                </div>
            </div>
            <!--/.navbar-header -->

            <div class="navbar-collapse collapse" id="navigation">

                <ul class="nav navbar-nav navbar-left">
                    <li class="active"><a href="index.jsp">Home</a>
                    </li>
                    <li class="dropdown yamm-fw">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200"> Shop <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="yamm-content">
                                <% ProductTypeCrud menuType=new ProductTypeCrud();
                                	HashMap<String,ArrayList<String>> map = menuType.fetchProductTypeMap();
                                for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                                	String productType=entry.getKey();
                                	ArrayList<String> productSubTypeList=entry.getValue();%>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <h5><%=productType %></h5>
											  <ul>
                                            <% for(String productSubType:productSubTypeList){ %>
                                                <li><a href="category?subtype=<%=productSubType%>"><%=productSubType %> </a>
                                                </li>
                                               <%} %>
                                            </ul>
                                        </div>
                                        <%} %>
                                    </div>
                                </div>
                                <!-- /.yamm-content -->
                            </li>
                        </ul>
                    </li>
                     <li class="dropdown yamm-fw">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200"> Recipes <b class="caret"></b></a>
                        <ul class="dropdown-menu" >
                            <li>
                                <div class="yamm-content">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <ul>
                                                <li><a href="ViewRecipes.html">View Recipes</a>
                                                </li>
											<% if(session.getAttribute("userType")!=null){
													if(session.getAttribute("userType").equals("V")) { %>
                                                <li><a href="AddRecipe.html">Add Recipes</a>
                                                </li>
                                                <% } } %>
                                                </ul>
                                                </div>
                                                </div>
                                                </div>
                                                </li>
                                                </ul>
                                                </li>
                 <% if(session.getAttribute("userType")!=null) {
                		if((Boolean)session.getAttribute("sessionExists")) { %>             
                <li class="dropdown yamm-fw">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200"> 
                       <% if(session.getAttribute("userType").equals("V")) { %>
                        Vendor
                        <% } else if(session.getAttribute("userType").equals("A")){  %>
						Admin
						<% } else { %>
                        User
                        <% } %> 
                        <b class="caret"></b></a>
                        <ul class="dropdown-menu" >
                            <li>
                                <div class="yamm-content">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <ul>
                                            <% if(session.getAttribute("userType").equals("V")) { %>
                                            <li><a href="AddProduct.jsp">Add Product</a>
                                                </li>
                                             <li><a href="Inventory.jsp">View Inventory</a>
                                                </li>
                                                <li><a href="changepass.html">Change Password</a>
                                                </li>
                                                <li><a href="logout">Logout</a>
                                                </li>
                                             <% }
												else if(session.getAttribute("userType").equals("C")){ %>
                                                <li><a href="changepass.html">Change Password</a>
                                                </li>
                                                <li><a href="CustomerOrders.jsp">Order History</a>
                                                </li>
                                                <li><a href="logout">Logout</a>
                                                </li>
												<% }
												else if(session.getAttribute("userType").equals("A")){ %>
												<li><a href="Inventory.jsp">View Inventory</a>
                                                </li>
                                                <li><a href="changepass.html">Change Password</a>
                                                </li>
                                                <li><a href="vendorreport.jsp">Vendor Sales Report</a>
                                                </li>
                                                <li><a href="vendorstats.jsp">Vendor Revenue Report</a>
                                                </li>
                                                <li><a href="viewvendorlist.jsp">View List of Vendors</a>
                                                </li>
                                                <li><a href="viewuserlist.jsp">View List of Buyer</a>
                                                </li>
                                                <li><a href="userreport.jsp">View Buyer Report</a>
                                                </li>
                                                <li><a href="logout">Logout</a>
                                                </li>
												<% } %>
                                                </ul>
                                                </div>
                                                </div>
                                                </div>
                                                </li>
                                                </ul>
                                                </li>
                                                <% } } %>
                 <!-- <li th:unless="${session.sessionExists} == true"><a href="#" data-toggle="modal" data-target="#login-modal">Login</a>
                    </li>
						<li th:case="V"> 
						<a href="AddRecipe.html" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200">Recipe 
						</a></li>
						<li th:case="C"> 
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200">Recipe 
						</a></li>

                    <li >
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200">Farmers </b></a>
                       
                    </li> -->
                </ul>

            </div>
            <!--/.nav-collapse -->

            <div class="navbar-buttons">

                <div class="navbar-collapse collapse right" id="basket-overview">
                    <a href="cart.jsp" class="btn btn-primary navbar-btn"><i class="fa fa-shopping-cart"></i><span class="hidden-sm"><%=OrderQuantity %> items in cart</span></a>
                </div>
                <!--/.nav-collapse -->

                <div class="navbar-collapse collapse right" id="search-not-mobile">
                    <button type="button" class="btn navbar-btn btn-primary" data-toggle="collapse" data-target="#search">
                        <span class="sr-only">Toggle search</span>
                        <i class="fa fa-search"></i>
                    </button>
                </div>

            </div>

            <div class="collapse clearfix" id="search">

                <form class="navbar-form" role="search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search">
                        <span class="input-group-btn">

			<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>

		    </span>
                    </div>
                </form>

            </div>
            <!--/.nav-collapse -->

        </div>
        <!-- /.container -->
    </div>
    <!-- /#navbar -->

    <!-- *** NAVBAR END *** -->
    
    <div id="all">

        <div id="content">
            <div class="container">

                <div class="col-md-12">
                    <ul class="breadcrumb">
                        <li><a href="#">Home</a>
                        </li>
                        <li>Checkout - Order review</li>
                    </ul>
                </div>
  					<%
                     cartItems = cart.fetchCartItems(uId); 
                     System.out.println("hello here is cart");
					 if(cartItems.size()>0){ 
					    float OrderSubTotal = orderDetails.getOrderSubTotal();
					%>
                <div class="col-md-9" id="checkout">

                    <div class="box">
                        <form method="post" action="Checkout.jsp">
                            <h1>Checkout - Order review</h1>
                            <ul class="nav nav-pills nav-justified">
                                <li class="active"><a href="#"><i class="fa fa-eye"></i><br>Order Review</a>
                                </li>
                                <li class="disabled"><a href="#"><i class="fa fa-map-marker"></i><br>Address</a>
                                </li>
                                <li class="disabled"><a href="#"><i class="fa fa-money"></i><br>Payment Method</a>
                                </li>
                            </ul>

                            <div class="content">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th colspan="2">Product</th>
                                                <th>Quantity</th>
                                                <th>Unit price</th>
                                                <th>Discount</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                          <% for(int i=0;i<cartItems.size();i++) {
                               			   long invId = cartItems.get(i).getInventoryId();
                               			   long orderId = cartItems.get(i).getOrderId();
                               			   long cartId = cartItems.get(i).getCartId();
	                        			   String name = cartItems.get(i).getProductName();
	                        			   String imageName = cartItems.get(i).getImageName(); 
	                        			   int units = cartItems.get(i).getInvQuantity();
	                        			   float unitPrice = cartItems.get(i).getUnitPrice();
	                        			   float discount = cartItems.get(i).getDiscount();
	                        			   float totalUnitPrice = unitPrice * units;
	                        			   float totalPrice = totalUnitPrice-((discount/100)*totalUnitPrice);%>
                                        <tr class = "cart_item">
                                            <td>
                                                <a href="#">
                                                <img src="<%=imageName%>" >
                                                <input type="hidden" name="orderId" value="<%=orderId%>">        
                                                </a>
                                            </td>
                                            <td><a href="#"><%=name%></a>
                                            </td>
                                            <td><%=units%></td>
                                            <td>$<%=unitPrice%></td>
                                            <td><%=discount%>%</td>
                                            <td>$<%=totalPrice%></td>
                                        </tr>
                                   <%}%> 
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th colspan="5">Total</th>
                                                <th>$<%=OrderSubTotal %></th>
                                            </tr>
                                        </tfoot>
                                    </table>

                                </div>
                                <!-- /.table-responsive -->
                            </div>
                            <!-- /.content -->

                            <div class="box-footer">
                                <div class="pull-left">
                                    <a href="cart.jsp" class="btn btn-default"><i class="fa fa-chevron-left"></i>Go back to the cart</a>
                                </div>
                                <div class="pull-right">
                                    <button type="submit" class="btn btn-primary">Continue to checkout<i class="fa fa-chevron-right"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- /.box -->


                </div>
                <!-- /.col-md-9 -->

                <div class="col-md-3">

                    <div class="box" id="order-summary">
                        <div class="box-header">
                            <h3>Order summary</h3>
                        </div>
                        <p class="text-muted">Shipping and additional costs are calculated based on the values you have entered.</p>

                           <div class="table-responsive">
                            <table class="table">
                                <tbody>
                                    <tr>
                                        <td>Order subtotal</td>
                                        <th>$<%=OrderSubTotal %></th>
                                    </tr>
                                    <tr>
                                        <td>Shipping and handling</td>
                                        <th>$0.00</th>
                                    </tr>
                                    <tr>
                                        <td>Tax</td>
                                        <th>$0.00</th>
                                    </tr>
                                    <tr class="total">
                                        <td>Total</td>
                                        <th>$<%=OrderSubTotal %></th>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>

                </div>
                <!-- /.col-md-3 -->
<%}else{ %>
 <div class="col-md-9" id="basket">
 <p >You currently have no items in your cart.</p>
 </div>
<%} %>
            </div>
            <!-- /.container -->
        </div>
        <!-- /#content -->


        <!-- *** FOOTER ***
 _________________________________________________________ -->
        <div id="footer" data-animate="fadeInUp">
            <div class="container">
                <div class="row">
                <div class="col-md-1">
                      </div>
                    <div class="col-md-3">
                        <h4>Pages</h4>

                        <ul>
                            <li><a href="text.html">About us</a>
                            </li>
                            <li><a href="text.html">Terms and conditions</a>
                            </li>
                            <li><a href="faq.html">FAQ</a>
                            </li>
                            <li><a href="contact.html">Contact us</a>
                            </li>
                        </ul>

                        </div>
                        <div class="col-md-1">
                      </div>
						<div class="col-md-3">
                        <h4>User section</h4>

                        <ul>
                            <li><a href="#" data-toggle="modal" data-target="#login-modal">Login</a>
                            </li>
                            <li><a href="register.html">Register</a>
                            </li>
                        </ul>

                        <hr class="hidden-md hidden-lg hidden-sm">

                    </div>
                    <!-- /.col-md-3 -->

                    
					<div class="col-md-1">
                      </div>
                    <div class="col-md-3">

                        <h4>Where to find us</h4>

                        <p><strong>Amazingly local</strong>
                            <br>107 S Indiana Ave
                            <br>Bloomington
                            <br>IN 47405
                            <br>
                            <strong>USA</strong>
                        </p>

                        <a href="contact.html">Go to contact page</a>

                        <hr class="hidden-md hidden-lg">

                    </div>
                    <!-- /.col-md-3 -->



                </div>
                <!-- /.row -->

            </div>
            <!-- /.container -->
        </div>
        <!-- /#footer -->

        <!-- /#footer -->

        <!-- *** FOOTER END *** -->




        <!-- *** COPYRIGHT ***
 _________________________________________________________ -->
        <div id="copyright">
            <div class="container">
                <div class="col-md-6">
                    <p class="pull-left">© 2015 Your name goes here.</p>

                </div>
                <div class="col-md-6">
                    <p class="pull-right">Template by <a href="https://bootstrapious.com/e-commerce-templates">Bootstrapious.com</a>
                         <!-- Not removing these links is part of the license conditions of the template. Thanks for understanding :) If you want to use the template without the attribution links, you can do so after supporting further themes development at https://bootstrapious.com/donate  -->
                    </p>
                </div>
            </div>
        </div>
        <!-- *** COPYRIGHT END *** -->

       

    </div>
    <!-- /#all -->


    

    <!-- *** SCRIPTS TO INCLUDE ***
 _________________________________________________________ -->
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.cookie.js"></script>
    <script src="js/waypoints.min.js"></script>
    <script src="js/modernizr.js"></script>
    <script src="js/bootstrap-hover-dropdown.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/front.js"></script>






</body>

</html>