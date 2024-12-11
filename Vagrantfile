Vagrant.configure("2") do |config|
  config.vm.define "jenkins" do |jenkins|
    jenkins.vm.box = "ubuntu/bionic64"
    jenkins.vm.hostname = "jenkins.local"
    jenkins.vm.network "private_network", ip: "192.168.56.101"
    jenkins.vm.provision "shell", inline: <<-SHELL
      sudo apt update
      sudo apt install -y openjdk-17-jdk wget
      wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -
      sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
      sudo apt update
      sudo apt install -y jenkins
      sudo systemctl enable jenkins
      sudo systemctl start jenkins
    SHELL
  end
end
