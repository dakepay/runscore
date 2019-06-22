var payChannelVM = new Vue({
	el : '#pay-channel',
	data : {
		payTypeDicts : [],
		tabWithPayTypeId : {},
		payChannels : [],
		games : [],
		showPayChannelFlag : true,
		selectedGame : {},

		editPayChannel : {},
		addOrUpdatePayChannelFlag : false,
		payChannelActionTitle : '',

		payTypes : [],
		showPayTypeFlag : false,
		selectedGameCategory : {},

		editPayType : {},
		addOrUpdatePayTypeFlag : false,
		payTypeActionTitle : '',

	},
	computed : {},
	created : function() {
	},
	mounted : function() {
		this.loadPayTypeDict();
		this.loadPayChannel();
	},
	methods : {

		loadPayTypeDict : function() {
			var that = this;
			that.$http.get('/recharge/findAllPayType', {}).then(function(res) {
				that.payTypeDicts = res.body.data;
				if (that.payTypeDicts.length > 0) {
					that.tabWithPayTypeId = that.payTypeDicts[0].id;
				}
			});
		},

		loadPayChannel : function() {
			var that = this;
			that.$http.get('/recharge/findAllPayChannel').then(function(res) {
				that.payChannels = res.body.data;
			});
		},

		showAddPayChannelModal : function() {
			this.addOrUpdatePayChannelFlag = true;
			this.payChannelActionTitle = '新增通道';
			this.editPayChannel = {
				payTypeId : this.tabWithPayTypeId,
				channelCode : '',
				channelName : '',
				bankName : '',
				accountHolder : '',
				bankCardAccount : '',
				orderNo : '',
				enabled : true
			};
		},

		showEditPayChannelModal : function(id) {
			var that = this;
			that.$http.get('/recharge/findPayChannelById', {
				params : {
					id : id
				}
			}).then(function(res) {
				that.editPayChannel = res.body.data;
				that.addOrUpdatePayChannelFlag = true;
				that.payChannelActionTitle = '编辑通道';
			});
		},

		addOrUpdatePayChannel : function() {
			var that = this;
			var editPayChannel = that.editPayChannel;
			if (editPayChannel.channelCode == null || editPayChannel.channelCode == '') {
				layer.alert('请输入通道code', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayChannel.channelName == null || editPayChannel.channelName == '') {
				layer.alert('请输入通道名称', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayChannel.bankName == null || editPayChannel.bankName == '') {
				layer.alert('请输入收款银行', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayChannel.accountHolder == null || editPayChannel.accountHolder == '') {
				layer.alert('请输入收款人', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayChannel.bankCardAccount == null || editPayChannel.bankCardAccount == '') {
				layer.alert('请输入卡号', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayChannel.orderNo == null || editPayChannel.orderNo == '') {
				layer.alert('请选择排序号', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			that.$http.post('/recharge/addOrUpdatePayChannel', editPayChannel).then(function(res) {
				layer.alert('操作成功!', {
					icon : 1,
					time : 3000,
					shade : false
				});
				that.addOrUpdatePayChannelFlag = false;
				that.loadPayChannel();
			});
		},

		delPayChannel : function(id) {
			var that = this;
			layer.confirm('确定要删除吗?', {
				icon : 7,
				title : '提示'
			}, function(index) {
				layer.close(index);
				that.$http.get('/recharge/delPayChannelById', {
					params : {
						id : id
					}
				}).then(function(res) {
					layer.alert('操作成功!', {
						icon : 1,
						time : 3000,
						shade : false
					});
					that.loadPayChannel();
				});
			});
		},

		toPayTypePage : function() {
			var that = this;
			that.$http.get('/recharge/findAllPayType', {}).then(function(res) {
				that.payTypes = res.body.data;
				that.showPayChannelFlag = false;
				that.showPayTypeFlag = true;
			});
		},

		backToPayChannelPage : function() {
			this.showPayTypeFlag = false;
			this.showPayChannelFlag = true;
			this.loadPayTypeDict();
			this.loadPayChannel();
		},

		showAddPayTypeModal : function() {
			this.addOrUpdatePayTypeFlag = true;
			this.payTypeActionTitle = '新增支付类型';
			this.editPayType = {
				type : '',
				name : '',
				bankCardFlag : false,
				orderNo : '',
				enabled : true
			};
		},

		showEditPayTypeModal : function(id) {
			var that = this;
			that.$http.get('/recharge/findPayTypeById', {
				params : {
					id : id
				}
			}).then(function(res) {
				that.editPayType = res.body.data;
				this.addOrUpdatePayTypeFlag = true;
				this.payTypeActionTitle = '编辑支付类型';
			});
		},

		addOrUpdatePayType : function() {
			var that = this;
			var editPayType = that.editPayType;
			if (editPayType.type == null || editPayType.type == '') {
				layer.alert('请输入支付类型', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayType.name == null || editPayType.name == '') {
				layer.alert('请输入名称', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}
			if (editPayType.orderNo == null || editPayType.orderNo == '') {
				layer.alert('请输入排序号', {
					title : '提示',
					icon : 7,
					time : 3000
				});
				return;
			}

			that.$http.post('/recharge/addOrUpdatePayType', editPayType).then(function(res) {
				layer.alert('操作成功!', {
					icon : 1,
					time : 3000,
					shade : false
				});
				that.addOrUpdatePayTypeFlag = false;
				that.toPayTypePage();
			});
		},

		delPayType : function(id) {
			var that = this;
			layer.confirm('确定要删除吗?', {
				icon : 7,
				title : '提示'
			}, function(index) {
				layer.close(index);
				that.$http.get('/recharge/delPayTypeById', {
					params : {
						id : id
					}
				}).then(function(res) {
					layer.alert('操作成功!', {
						icon : 1,
						time : 3000,
						shade : false
					});
					that.toPayTypePage();
				});
			});
		}

	}
});