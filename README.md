# ECommerce-website-Backend
Java Springboot backend for production grade ecommerce enterprise. It includes modules for Authentication, Inventory Management, Shopping Cart, Wish List, Ordering, Delivery Status and Payment Gateway using Razor-Pay.

Authentication
Cookie based Authentication. Two-step email based Registration. Only two roles define ADMIN_ROLE and USER_ROLE. Admin has certain priviliges which a generic user doesn't. The admin privilidges are to manage inventory, look at user details, user authority, shopping cart, wishlist, orders, payment status, delivery status of all users

Inventory Management
Products can be added and deleted one at a time and in a list. The product can be updated one at a time. Addition of products with Excel Sheets

Shopping Cart & WishList
Crud based operation for Shopping Cart and Wish List. Same product with different product attributes can be to Shopping Cart but not the Wish List

Ordering and Delivery Status
A product attempted to buy is added to the list of Orders and the payment status determines a successful order. Delivery Status is updated to Received, Packaging , Dispatched or Delivered.

Payment Gateway
Razorpay gateway is used for making payments and keeping track of payment status.

Postgresql Database Schema
<img width="1792" height="1248" alt="image" src="https://github.com/user-attachments/assets/07f262c3-26f9-4ac3-9fa7-9e40d8ec0358" />

Screenshot:
<img width="1714" height="846" alt="Screenshot 2025-08-30 182249" src="https://github.com/user-attachments/assets/c12a1306-201e-4399-a617-6621fbc8b306" />
