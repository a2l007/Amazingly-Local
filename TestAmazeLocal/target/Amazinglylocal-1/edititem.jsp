<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="robots" content="all,follow">
    <meta name="googlebot" content="index,follow,snippet,archive">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Obaju e-commerce template">
    <meta name="author" content="Ondrej Svestka | ondrejsvestka.cz">
    <meta name="keywords" content="">
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
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
    <link href="css/Pages/addProduct.css" rel="stylesheet">
    
    <script src="js/respond.min.js"></script>
    <%@page import="com.iu.amazelocal.models.Inventory"%>
    <%@page import ="com.fasterxml.jackson.databind.ObjectMapper"%>
	<%@page import="java.util.ArrayList"%>
	<%@page import="java.util.Arrays"%>
    <link rel="shortcut icon" href="favicon.png">

</head>
<script type="text/javascript">
</script>

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
                    <li><a href="#" data-toggle="modal" data-target="#login-modal">Login</a>
                    </li>
                    <li><a href="register.html">Register</a>
                    </li>
                    <li><a href="contact.html">Contact</a>
                    </li>
                    <li><a href="#">Recently viewed</a>
                    </li>
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
                                <input type="text" class="form-control" id="fname-modal" placeholder="First Name">
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="lname-modal" placeholder="Last Name">
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="email-modal" placeholder="email">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" id="password-modal" placeholder="password">
                            </div>
							<div class="form-group">
                                <input type="text" class="form-control" id="phone-modal" placeholder="Phone Number">
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" id="address-modal" placeholder="Mailing Address">
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
                    <img src="img/logo-small.png" alt="Obaju logo" class="visible-xs"><span class="sr-only">Obaju - go to homepage</span>
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
                    <a class="btn btn-default navbar-toggle" href="basket.html">
                        <i class="fa fa-shopping-cart"></i>  <span class="hidden-xs">3 items in cart</span>
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
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <h5>Vegetables</h5>
                                            <ul>
                                                <li><a href="category.html">Tomato</a>
                                                </li>
                                                <li><a href="category.html">Potato</a>
                                                </li>
                                                <li><a href="category.html">Onion</a>
                                                </li>
                                                <li><a href="category.html">Broccoli</a>
                                                </li>
                                                <li><a href="category.html">Egg Plant</a>
                                                </li>
                                                <li><a href="category.html">Broccoli</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-sm-3">
                                            <h5>Fruits</h5>
                                            <ul>
                                                <li><a href="category.html">Apple</a>
                                                </li>
                                                <li><a href="category.html">Citrus fruits</a>
                                                </li>
                                                <li><a href="category.html">Banana</a>
                                                </li>
                                                <li><a href="category.html">Melon</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-sm-3">
                                            <h5>Meat</h5>
                                            <ul>
                                                <li><a href="category.html">Beef</a>
                                                </li>
                                                <li><a href="category.html">Chicken</a>
                                                </li>
                                                <li><a href="category.html">Pork</a>
                                                </li>
                                                <li><a href="category.html">Turkey</a>
                                                </li>                                  
                                            </ul>
                                        </div>
                                        <div class="col-sm-3">
                                            <h5>Dairy Products</h5>
                                            <ul>
                                                <li><a href="category.html">Milk</a>
                                                </li>
                                                <li><a href="category.html">Butter</a>
                                                </li>
                                                <li><a href="category.html">Cheese</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.yamm-content -->
                            </li>
                        </ul>
                    </li>

					<li>
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200">Recipe </b></a>                       
					</li>

                    <li >
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-delay="200">Farmers </b></a>
                       
                    </li>
                </ul>

            </div>
            <!--/.nav-collapse -->

            <div class="navbar-buttons">

                <div class="navbar-collapse collapse right" id="basket-overview">
                    <a href="basket.html" class="btn btn-primary navbar-btn"><i class="fa fa-shopping-cart"></i><span class="hidden-sm">3 items in cart</span></a>
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
                        <li>Products</li>
                        <li>Edit Product</li>
                    </ul>
                </div>
				<div class="col-md-2">
				</div>
                <div class="col-md-8">
                    <div class="box">
                        <h1>Edit product</h1>
                        <p class="lead">Want to edit product details?</p>
                        <p>Edit the product details. Add a new sale, change its description or edit any other details.</p>
                        <hr>
                       <% Inventory inv = (Inventory) request.getAttribute("inv");%>
                         <form action="updateProduct" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="prodname">Product Name</label>
                                <input type="text" class="form-control" name="prodname" value=" <%=inv.getProductName() %>" required>
                                <input type="hidden" value=<%=inv.getInventoryId()%> name="invId"/>
                            </div>
                          
                             <div class="form-group">
                                <label for="desc">Product Description</label>
                                <input type="text" class="form-control" name="desc" value="<%=inv.getDescription() %>" required>
                            </div>
                             <div class="form-group">
                                <label for="prodcat">Product Category</label>	
 					            <select id = "category" name = "prodcat" class="form-control" required>
								</select>
	                         </div>
	                         <div class="form-group">
                                <label for="prodsubcat">Product Sub Category</label>	
 					            <select id = "subCat" name = "prodsubcat" class="form-control" required>
								</select>
	                         </div>
	                         <div class="form-group subCat subCatNameHide">
								<label for ="subcatname"> New Sub Category Name</label>
								<input id="subCatName" type="text" class="form-control" name="subcatname">
	                         </div>
                            <div class="form-group">
                                <label for="price">Price</label>
                                <input type="number" class="form-control" min ="0" name="price" step="0.01" value= <%=inv.getPrice() %> required>
                            </div>
                            <div class="form-group">
                                <label for="quantity">Quantity</label>
                                <input type="number" class="form-control"  min ="0" step="1" name="quantity" value= <%=inv.getQuantity() %> required>
                            </div>
                            <div class="form-group">
                                <label for="unit">Unit</label>
                                <input type="text" class="form-control" name="unit" value= <%=inv.getUnit() %> required>
                            </div>
                             <div class="form-group">
                                <label for="cal">Calories Per Unit</label>
                                <input type="number"  min ="0" class="form-control" name="cal" step="0.01" value= <%=inv.getCalories() %> required>
                            </div>
                            <div class="form-group">
                                <label for="salepercent">Sale Percentage</label>
                                <input type="number" min ="0" class="form-control" name="salepercent" step="0.01" value= <%=inv.getSale() %> required>
                            </div >
                            <div class="form-group" >
                           	<% String imageName = inv.getImageName();
                                	System.out.println("Image name is:" + imageName);%>
                                		<label for="imagecb">Check the image </label>
                                		<input name="imagecb" type="checkbox" value="<%=imageName%>">
                                		<img src="<%=imageName%>" style="height: 60px;width: 80px;">
                                	<% 
                           	%> 
                            </div>
                            <div class="form-group" >
                              <label for="upload-file-input">Upload your file:</label>
  							  <input id="upload-file-input" type="file" name="uploadfile" accept="image/*" required/>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary"><i class="fa fa-sign-in"></i> Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            <div class="col-md-2">
            </div>
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
    <script src="js/jquery.cookie.js"></script>
    <script src="js/waypoints.min.js"></script>
    <script src="js/modernizr.js"></script>
    <script src="js/bootstrap-hover-dropdown.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <script src="js/front.js"></script>
    <script src="js/Pages/AddProduct.js"></script>


</body>

</html>