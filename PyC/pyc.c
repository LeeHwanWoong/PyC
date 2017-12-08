#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char* argv[]) {
	char* filename = argv[1];
	char command1[100] = "java CodeGenerator ";
	char command2[100] = "javac ";
	char command3[100] = "java ";
	char command4[100] = ".java";
	int namelen = 0;
	int i;

	for(i = 0;i<100;i++){
		if(filename[i] == '\0'){
			break;
		}
		namelen++;
	}

	strcat(command1,filename);
	filename[namelen-4] = '\0';
	strcat(command2,filename);
	strcat(command2,command4);
	strcat(command3,filename);

	system(command1);
	system(command2);
	system(command3);

	return 0;
}