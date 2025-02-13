-- Dữ liệu cho bảng Role
INSERT INTO [Role] ([role_id], [role_name]) VALUES
(1, N'Admin'),
(2, N'Customer'),
(3, N'Staff');

-- Dữ liệu cho bảng User
INSERT INTO [User] ([user_id], [role_id], [email], [password], [phone_number], [address], [status]) VALUES
(1, 1, N'admin@example.com', N'password123', N'123456789', N'123 Admin Street', 1),
(2, 2, N'customer1@example.com', N'password123', N'987654321', N'456 Customer Avenue', 1),
(3, 3, N'staff1@example.com', N'password123', N'555555555', N'789 Staff Blvd', 1);

-- Dữ liệu cho bảng Category
INSERT INTO [Category] ([category_id], [name]) VALUES
(1, N'Clothing'),
(2, N'Electronics'),
(3, N'Furniture');

-- Dữ liệu cho bảng Product
INSERT INTO [Product] ([product_id], [category_id], [product_name], [description], [discount], [status]) VALUES
(1, 1, N'T-Shirt', N'Comfortable cotton t-shirt', 10, 1),
(2, 2, N'Smartphone', N'Latest model smartphone with advanced features', 15, 1),
(3, 3, N'Sofa', N'Spacious and stylish sofa', 20, 1);

-- Dữ liệu cho bảng Size
INSERT INTO [Size] ([size_id], [size]) VALUES
(1, N'Small'),
(2, N'Medium'),
(3, N'Large');

-- Dữ liệu cho bảng Color
INSERT INTO [Color] ([color_id], [color]) VALUES
(1, N'Red'),
(2, N'Blue'),
(3, N'Green');

-- Dữ liệu cho bảng ProductThumbnail
INSERT INTO [ProductThumbnail] ([thumbnail_id], [product_id], [thumbnail_url]) VALUES
(1, 1, N'http://example.com/tshirt.jpg'),
(2, 2, N'http://example.com/smartphone.jpg'),
(3, 3, N'http://example.com/sofa.jpg');

-- Dữ liệu cho bảng ProductPrice
INSERT INTO [ProductPrice] ([ProductPrice_id], [product_id], [color_id], [price]) VALUES
(1, 1, 1, 200),
(2, 1, 2, 210),
(3,2,2,300),
(4, 2, 3, 500);

-- Dữ liệu cho bảng ProductQuantity
INSERT INTO [ProductQuantity] ([ProductQuantity_id], [ProductPrice_id], [size_id], [quantity]) VALUES
(1, 1, 1, 50),
(3,2,2,15),
(4,2,3,10),
(2, 1, 2, 10);


