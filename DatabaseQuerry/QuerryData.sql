SELECT 
    p.product_id AS [Product ID],
    p.product_name AS [Product Name],
    c.name AS [Category],
    p.description AS [Description],
    p.discount AS [Discount (%)],
    pp.price AS [Price],
    clr.color AS [Color],
    sz.size AS [Size],
    pq.quantity AS [Quantity Available],
    pt.thumbnail_url AS [Thumbnail URL]
FROM 
    [Product] p
LEFT JOIN 
    [Category] c ON p.category_id = c.category_id
LEFT JOIN 
    [ProductPrice] pp ON p.product_id = pp.product_id
LEFT JOIN 
    [Color] clr ON pp.color_id = clr.color_id
LEFT JOIN 
    [ProductQuantity] pq ON pp.ProductPrice_id = pq.ProductPrice_id
LEFT JOIN 
    [Size] sz ON pq.size_id = sz.size_id
LEFT JOIN 
    [ProductThumbnail] pt ON p.product_id = pt.product_id;


/*Đếm ra số lượng sản phẩm theo id */
SELECT 
    p.product_id,
    p.product_name,
    SUM(pq.quantity) AS [Total Quantity]
FROM 
    [Product] p
JOIN 
    [ProductPrice] pp ON p.product_id = pp.product_id
JOIN 
    [ProductQuantity] pq ON pp.ProductPrice_id = pq.ProductPrice_id
WHERE 
    p.product_id = 1
GROUP BY 
    p.product_id, p.product_name;
