CREATE TABLE [Role] (
  [role_id] int PRIMARY KEY,
  [role_name] nvarchar(255)
);
GO

CREATE TABLE [User] (
  [user_id] int PRIMARY KEY,
  [role_id] int,
  [email] nvarchar(255),
  [password] nvarchar(255),
  [phone_number] nvarchar(255),
  [address] nvarchar(255),
  [status] bit,
  CONSTRAINT FK_User_Role FOREIGN KEY ([role_id]) REFERENCES [Role] ([role_id])
);
GO

CREATE TABLE [Category] (
  [category_id] int PRIMARY KEY,
  [name] nvarchar(255)
);
GO

CREATE TABLE [Product] (
  [product_id] int PRIMARY KEY,
  [category_id] int,
  [product_name] nvarchar(255),
  [description] nvarchar(255),
  [discount] int,
  [status] bit,
  CONSTRAINT FK_Product_Category FOREIGN KEY ([category_id]) REFERENCES [Category] ([category_id])
);
GO

CREATE TABLE [Size] (
  [size_id] int PRIMARY KEY,
  [size] nvarchar(255)
);
GO

CREATE TABLE [Color] (
  [color_id] int PRIMARY KEY,
  [color] nvarchar(255)
);
GO

CREATE TABLE [ProductThumbnail] (
  [thumbnail_id] int PRIMARY KEY,
  [product_id] int,
  [thumbnail_url] nvarchar(255),
  CONSTRAINT FK_ProductThumbnail_Product FOREIGN KEY ([product_id]) REFERENCES [Product] ([product_id])
);
GO

CREATE TABLE [ProductPrice] (
  [ProductPrice_id] int PRIMARY KEY,
  [product_id] int,
  [color_id] int,
  [price] int,
  CONSTRAINT FK_ProductPrice_Product FOREIGN KEY ([product_id]) REFERENCES [Product] ([product_id]),
  CONSTRAINT FK_ProductPrice_Color FOREIGN KEY ([color_id]) REFERENCES [Color] ([color_id])
);
GO

CREATE TABLE [ProductQuantity] (
  [ProductQuantity_id] int PRIMARY KEY,
  [ProductPrice_id] int,
  [size_id] int,
  [quantity] int,
  CONSTRAINT FK_ProductQuantity_ProductPrice FOREIGN KEY ([ProductPrice_id]) REFERENCES [ProductPrice] ([ProductPrice_id]),
  CONSTRAINT FK_ProductQuantity_Size FOREIGN KEY ([size_id]) REFERENCES [Size] ([size_id])
);
GO

CREATE TABLE [Orders] (
  [order_id] int PRIMARY KEY,
  [user_id] int,
  [order_date] datetime,
  [total_price] int,
  [status] nvarchar(255),
  [voucher_id] int,
  [shipping_address] nvarchar(255),
  CONSTRAINT FK_Orders_User FOREIGN KEY ([user_id]) REFERENCES [User] ([user_id])
);
GO

CREATE TABLE [OrderDetails] (
  [order_detail_id] int PRIMARY KEY,
  [order_id] int,
  [ProductPrice_id] int,
  [size_id] int,
  [quantity] int,
  [price] int,
  CONSTRAINT FK_OrderDetails_Order FOREIGN KEY ([order_id]) REFERENCES [Orders] ([order_id]),
  CONSTRAINT FK_OrderDetails_ProductPrice FOREIGN KEY ([ProductPrice_id]) REFERENCES [ProductPrice] ([ProductPrice_id]),
  CONSTRAINT FK_OrderDetails_Size FOREIGN KEY ([size_id]) REFERENCES [Size] ([size_id])
);
GO

CREATE TABLE [Blog] (
  [blog_id] int PRIMARY KEY,
  [title] nvarchar(255),
  [content] text,
  [author] nvarchar(255),
  [created_at] datetime,
  [status] bit,
  [thumbnail] nvarchar(255)
);
GO

CREATE TABLE [Banner] (
  [banner_id] int PRIMARY KEY,
  [image_url] nvarchar(255),
  [link_url] nvarchar(255),
  [display_order] int,
  [status] bit
);
GO

CREATE TABLE [Revenue] (
  [revenue_id] int PRIMARY KEY,
  [date] date,
  [total_orders] int,
  [total_revenue] int
);
GO

CREATE TABLE [RevenueDetails] (
  [revenue_detail_id] int PRIMARY KEY,
  [revenue_id] int,
  [product_id] int,
  [quantity_sold] int,
  [revenue] int,
  CONSTRAINT FK_RevenueDetails_Revenue FOREIGN KEY ([revenue_id]) REFERENCES [Revenue] ([revenue_id]),
  CONSTRAINT FK_RevenueDetails_Product FOREIGN KEY ([product_id]) REFERENCES [Product] ([product_id])
);
GO

CREATE TABLE [Feedback] (
  [feedback_id] int PRIMARY KEY,
  [product_id] int,
  [user_id] int,
  [feedback_content] text,
  [rating] int,
  [created_at] datetime,
  [status] bit,
  CONSTRAINT FK_Feedback_Product FOREIGN KEY ([product_id]) REFERENCES [Product] ([product_id]),
  CONSTRAINT FK_Feedback_User FOREIGN KEY ([user_id]) REFERENCES [User] ([user_id])
);
GO

CREATE TABLE [FeedbackReply] (
  [reply_id] int PRIMARY KEY,
  [feedback_id] int,
  [reply_content] nvarchar(255),
  CONSTRAINT FK_FeedbackReply_Feedback FOREIGN KEY ([feedback_id]) REFERENCES [Feedback] ([feedback_id])
);
GO

CREATE TABLE [Voucher] (
  [voucher_id] int PRIMARY KEY,
  [code] nvarchar(255),
  [discount_value] int,
  [min_order_value] int,
  [start_date] datetime,
  [end_date] datetime,
  [status] bit
);
GO

ALTER TABLE [Orders]
ADD CONSTRAINT FK_Orders_Voucher FOREIGN KEY ([voucher_id]) REFERENCES [Voucher] ([voucher_id]);
GO
