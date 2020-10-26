K-COMPUTER-3\SQLEXPRESS


payment

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Clients](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ClientNumber] [nchar](10) NULL,
	[PassWordHash] [nchar](100) NULL,
	[LastName] [nchar](100) NULL,
	[FirstName] [nchar](100) NULL,
	[MiddleName] [nchar](100) NULL
 CONSTRAINT [PK_ID_Clients] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

CREATE UNIQUE NONCLUSTERED INDEX [I_ClientNumber_Clients] ON [dbo].[Clients]
(
	[ClientNumber] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO



------------------------

INSERT INTO [dbo].[Clients]([ClientNumber], [PassWordHash], [LastName], [FirstName], [MiddleName])
VALUES   
(N'4545', N'3f74ed1b90de7d06a51891228750fcb1', N'Петров', N'Петр', N'Петрович'),
(N'5656', N'6cc5a3e8b68a97ca3cea363febf87151', N'Иванов', N'Иван', N'Иванович'),
(N'0000', N'ced165163e51e06e01dc44c35fea3eaf', N'Хунта', N'Кристобаль', N'Хозевич');

---

